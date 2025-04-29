package com.devtrack.utils.exceptions;

import lombok.Builder;

import java.util.Date;

@Builder
public record CustomError(Integer errorCode, String error, Date timestamp) { }
