package com.likelion.moneylogbackend.domain.translog;

import com.likelion.moneylogbackend.global.config.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller@RequestMapping("/moneylog")
@RequiredArgsConstructor
public class TransLogController {
    private final TransLogService transLogService;

    @GetMapping
    public ResponseEntity<Page<TransLogResponseDto>> getPages(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                              TransLogDateRequest date,
                                                              Pageable pageable){
        return ResponseEntity.ok(transLogService.listPaging(userDetails.getUser(),date,pageable));
    }

    @PostMapping
    public ResponseEntity<TransLogResponseDto> createTransLog(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                              @RequestBody TransLogRequestDto dto){

        return ResponseEntity.ok(transLogService.create(userDetails.getUser(),dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransLog(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long id
    ) {
        transLogService.deleteTransLog(userDetails.getUser(), id);
        return ResponseEntity.noContent().build(); // 204 No Content 반환
    }
    @PutMapping("/{id}")
    public ResponseEntity<TransLogResponseDto> modifyTransLog(
            @RequestBody TransLogRequestDto dto,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long id
    ) {
       return ResponseEntity.ok(transLogService.updateTransLog(userDetails.getUser(),id,dto));
    }
}
