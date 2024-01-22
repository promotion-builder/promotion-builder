package kr.njw.promotionbuilder.sample.application;

import kr.njw.promotionbuilder.common.dto.BaseResponseStatus;
import kr.njw.promotionbuilder.common.exception.BaseException;
import kr.njw.promotionbuilder.sample.application.dto.*;
import kr.njw.promotionbuilder.sample.entity.Sample;
import kr.njw.promotionbuilder.sample.repository.SampleRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class SampleServiceImpl implements SampleService {
    private final SampleRepository sampleRepository;

    @Override
    public FindSamplesResponse findSamples(FindSamplesRequest request) {
        final int MAX_PAGING_SIZE = 1000;

        Page<Sample> samplePage = this.sampleRepository.findByDeletedAtNullOrderByIdDesc(PageRequest.of(request.getPage() - 1, Math.min(request.getPagingSize(), MAX_PAGING_SIZE)));

        FindSamplesResponse response = new FindSamplesResponse();
        response.setPage(samplePage.getNumber() + 1);
        response.setPageSize(samplePage.getNumberOfElements());
        response.setPagingSize(samplePage.getSize());
        response.setTotalPage(samplePage.getTotalPages());
        response.setTotalSize(samplePage.getTotalElements());
        response.setSamples(samplePage.getContent().stream().map(sample -> {
            FindSampleResponse sampleResponse = new FindSampleResponse();
            sampleResponse.setId(sample.getId());
            sampleResponse.setName(sample.getName());
            sampleResponse.setStatus(sample.getStatus());
            sampleResponse.setCreatedAt(sample.getCreatedAt());
            return sampleResponse;
        }).toList());

        return response;
    }

    @Override
    public Optional<FindSampleResponse> findSample(FindSampleRequest request) {
        Sample sample = this.sampleRepository.findFirstByNameAndDeletedAtNull(request.getName()).orElse(null);

        if (sample == null) {
            return Optional.empty();
        }

        FindSampleResponse response = new FindSampleResponse();
        response.setId(sample.getId());
        response.setName(sample.getName());
        response.setStatus(sample.getStatus());
        response.setCreatedAt(sample.getCreatedAt());

        return Optional.of(response);
    }

    @Override
    public CreateSampleResponse createSample(CreateSampleRequest request) {
        if (StringUtils.containsIgnoreCase(request.getName(), "금지")) {
            throw new BaseException(BaseResponseStatus.SAMPLE_BAD_NAME);
        }

        Sample sample = Sample.builder()
                .user(request.getUserId())
                .name(request.getName())
                .status(request.getStatus())
                .build();

        this.sampleRepository.saveAndFlush(sample);

        CreateSampleResponse response = new CreateSampleResponse();
        response.setId(sample.getId());

        return response;
    }
}
