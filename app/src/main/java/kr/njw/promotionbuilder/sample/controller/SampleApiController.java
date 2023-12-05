package kr.njw.promotionbuilder.sample.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import kr.njw.promotionbuilder.common.dto.BaseResponse;
import kr.njw.promotionbuilder.common.dto.BaseResponseStatus;
import kr.njw.promotionbuilder.common.exception.BaseException;
import kr.njw.promotionbuilder.sample.application.SampleService;
import kr.njw.promotionbuilder.sample.application.dto.*;
import kr.njw.promotionbuilder.sample.controller.dto.CreateSampleApiRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/samples")
public class SampleApiController {
    private final SampleService sampleService;

    @Operation(summary = "샘플 목록", description = "")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content()),
    })
    @GetMapping("")
    public ResponseEntity<BaseResponse<FindSamplesResponse>> findSamples(
            @Parameter(description = "페이지 번호")
            @Positive(message = "must be greater than 0")
            @RequestParam(value = "page", defaultValue = "1")
            Integer page,

            @Parameter(description = "페이징 사이즈 (최대 20)")
            @Min(value = 1, message = "must be greater than or equal to 1")
            @Max(value = 100, message = "must be less than or equal to 20")
            @RequestParam(value = "pagingSize", defaultValue = "20")
            Integer pagingSize
    ) {
        FindSamplesRequest request = new FindSamplesRequest();
        request.setPage(page);
        request.setPagingSize(pagingSize);

        FindSamplesResponse response = this.sampleService.findSamples(request);

        return ResponseEntity.ok(new BaseResponse<>(response));
    }

    @Operation(summary = "샘플 찾기", description = """
            샘플 찾기

            이름으로 샘플을 찾을 수 있습니다.""")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content()),
            @ApiResponse(responseCode = "404", content = @Content())
    })
    @GetMapping("/search")
    public ResponseEntity<BaseResponse<FindSampleResponse>> findSample(
            @Parameter(description = "샘플 이름", example = "샘플")
            @NotBlank(message = "must not be blank")
            @Size(min = 2, max = 10, message = "size must be between 2 and 10")
            @RequestParam(value = "name")
            String name
    ) {
        FindSampleRequest request = new FindSampleRequest();
        request.setName(name);

        FindSampleResponse response = this.sampleService.findSample(request).orElse(null);

        if (response == null) {
            throw new BaseException(BaseResponseStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(new BaseResponse<>(response));
    }

    @SecurityRequirement(name = "accessToken")
    @Operation(summary = "샘플 생성", description = """
            샘플 생성

            accessToken이 있어야 요청 가능""")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = """
                    금지된 키워드가 포함되어 있습니다. (code: 98001)""", content = @Content()),
            @ApiResponse(responseCode = "403", content = @Content())
    })
    @PostMapping("")
    public ResponseEntity<BaseResponse<CreateSampleResponse>> createSample(Principal principal,
                                                                           @Valid @RequestBody CreateSampleApiRequest apiRequest) {
        if (principal == null) {
            throw new BaseException(BaseResponseStatus.FORBIDDEN);
        }

        CreateSampleRequest request = new CreateSampleRequest();
        request.setUserId(Long.valueOf(principal.getName()));
        request.setName(apiRequest.getName());
        request.setStatus(apiRequest.getStatus());

        return ResponseEntity.ok(new BaseResponse<>(this.sampleService.createSample(request)));
    }
}
