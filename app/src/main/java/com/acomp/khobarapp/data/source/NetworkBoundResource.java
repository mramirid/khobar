package com.acomp.khobarapp.data.source;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.acomp.khobarapp.data.source.remote.ApiResponse;
import com.acomp.khobarapp.utils.AppExecutors;
import com.acomp.khobarapp.vo.Resource;

public abstract class NetworkBoundResource<ResultType, RequestType> {

	private MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();
	private AppExecutors executors;

	protected void onFetchFailed() { }

	protected abstract LiveData<ResultType> loadFromDB();

	protected abstract Boolean shouldFetch(ResultType data);

	protected abstract LiveData<ApiResponse<RequestType>> createCall();

	protected abstract void saveCallResult(RequestType data);

	NetworkBoundResource(AppExecutors executors) {
		this.executors = executors;
		result.setValue(Resource.loading(null));

		LiveData<ResultType> dbSource = loadFromDB();

		result.addSource(dbSource, resultType -> {
			result.removeSource(dbSource);

			if (shouldFetch(resultType))
				fetchFromNetwork(dbSource);
			else
				result.addSource(dbSource, resultType1 -> result.setValue(Resource.success(resultType1)));
		});
	}

	private void fetchFromNetwork(LiveData<ResultType> dbSource) {
		LiveData<ApiResponse<RequestType>> apiResponse = createCall();

		result.addSource(dbSource, resultType -> result.setValue(Resource.loading(resultType)));

		result.addSource(apiResponse, requestTypeApiResponse -> {
			result.removeSource(apiResponse);
			result.removeSource(dbSource);

			switch (requestTypeApiResponse.status) {
				case SUCCESS:
					executors.diskIO().execute(() -> {
						saveCallResult(requestTypeApiResponse.body);
						executors.mainThread().execute(() -> {
							result.addSource(loadFromDB(), resultType -> {
								result.setValue(Resource.success(resultType));
							});
						});
					});
					break;
				case EMPTY:
					executors.mainThread().execute(() -> {
						result.addSource(loadFromDB(), resultType -> result.setValue(Resource.success(resultType)));
					});
					break;
				case ERROR:
					onFetchFailed();
					result.addSource(dbSource, resultType -> {
						result.setValue(Resource.error(requestTypeApiResponse.message, resultType));
					});
					break;
			}
		});
	}

	LiveData<Resource<ResultType>> asLiveData() {
		return result;
	}
}
