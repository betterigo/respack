package com.troila.cloud.respack.core;

import com.fasterxml.jackson.databind.JsonNode;

public interface ResultPackager {
	PackEntity pack(RespAttrs attrs, JsonNode data);
}
