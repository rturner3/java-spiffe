package io.spiffe.spiffeid;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrustDomainTest {

    @ParameterizedTest
    @MethodSource("provideTestTrustDomain")
    void testParseTrustDomain(String input, Object expected) {
        TrustDomain result;
        try {
            result = TrustDomain.of(input);
            assertEquals(expected, result.getName());
        } catch (Exception e) {
            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    void testNewSpiffeId() {
        TrustDomain trustDomain = TrustDomain.of("test.domain");
        SpiffeId spiffeId = trustDomain.newSpiffeId("path1", "host");

        assertEquals(trustDomain, spiffeId.getTrustDomain());
        assertEquals("/path1/host", spiffeId.getPath());
    }

    @Test
    void testToString() {
        TrustDomain trustDomain = TrustDomain.of("test.domain");
        assertEquals("test.domain", trustDomain.toString());
    }

    @Test
    void testGetName() {
        TrustDomain trustDomain = TrustDomain.of("test.domain");
        assertEquals("test.domain", trustDomain.getName());
    }

    static Stream<Arguments> provideTestTrustDomain() {
        return Stream.of(
                Arguments.of("", "Trust domain cannot be empty"),
                Arguments.of(null, "trustDomain is marked non-null but is null"),
                Arguments.of("   DomAin.TesT  ", "domain.test"),
                Arguments.of(" spiffe://domaiN.Test ", "domain.test"),
                Arguments.of("spiffe://domain.test/path/element", "domain.test"),
                Arguments.of("spiffe://domain.test/spiffe://domain.test/path/element", "domain.test"),
                Arguments.of("spiffe://domain.test/spiffe://domain.test:80/path/element", "domain.test"),
                Arguments.of("http://domain.test", "Invalid scheme"),
                Arguments.of("spiffe:// domain.test ", "Illegal character in authority at index 9: spiffe:// domain.test"),
                Arguments.of("://domain.test", "Expected scheme name at index 0: ://domain.test"),
                Arguments.of("spiffe:///path/element", "Trust domain cannot be empty"),
                Arguments.of("/path/element", "Trust domain cannot be empty"),
                Arguments.of("spiffe://domain.test:80", "Trust Domain: port is not allowed")
        );
    }
}
