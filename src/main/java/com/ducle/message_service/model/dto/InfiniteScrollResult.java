package com.ducle.message_service.model.dto;

import java.util.List;

public record InfiniteScrollResult<T>(
        List<T> data,
        boolean hasMore) {

}
