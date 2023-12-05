package kr.njw.promotionbuilder.sample.application;

import kr.njw.promotionbuilder.sample.application.dto.*;

import java.util.Optional;

public interface SampleService {
    FindSamplesResponse findSamples(FindSamplesRequest request);

    Optional<FindSampleResponse> findSample(FindSampleRequest request);

    CreateSampleResponse createSample(CreateSampleRequest request);
}
