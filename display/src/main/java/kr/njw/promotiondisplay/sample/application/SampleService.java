package kr.njw.promotiondisplay.sample.application;

import kr.njw.promotiondisplay.sample.application.dto.*;

import java.util.Optional;

public interface SampleService {
    FindSamplesResponse findSamples(FindSamplesRequest request);

    Optional<FindSampleResponse> findSample(FindSampleRequest request);

    CreateSampleResponse createSample(CreateSampleRequest request);
}
