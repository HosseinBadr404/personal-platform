package dev.hossein.personalplatform.auth;

public record CsrfResponse(
        String token,
        String headerName,
        String parameterName
) {
}
