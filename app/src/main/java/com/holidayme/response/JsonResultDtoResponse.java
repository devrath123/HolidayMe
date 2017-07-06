package com.holidayme.response;


import com.holidayme.data.JsonResultDto;

/**
 * Created by santosh.patar on 07-08-2015.
 */
public class JsonResultDtoResponse {

    private JsonResultDto JsonResult;

    private ErrorResponseBase Error;

    public JsonResultDto getJsonResult() {
        return JsonResult;
    }

    public void setJsonResult(JsonResultDto jsonResult) {
        JsonResult = jsonResult;
    }

    public ErrorResponseBase getError() {
        return Error;
    }

    public void setError(ErrorResponseBase error) {
        Error = error;
    }
}
