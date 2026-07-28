package com.likelion.moneylogbackend.domain.translog;

import com.likelion.moneylogbackend.domain.user.User;
import com.likelion.moneylogbackend.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class TransLogService {
    private final TransLogRepository transLogRepository;
    private final UserRepository userRepository;

    //내역 생성
    public TransLogResponseDto create(User user, TransLogRequestDto req) {
        TransLog newTransLog = TransLog.builder()
                .type(req.type())
                .amount(req.amount())
                .category(req.category())
                .date(req.date())
                .description(req.description())
                .user(user)
                .build();

        return TransLogResponseDto.from(transLogRepository.save(newTransLog));
    }

    //내역 조회, 페이징
    public Page<TransLogResponseDto> listPaging(User user, TransLogDateRequest dateReq, Pageable pageable) {
        //조회 시작 날짜를 들고 오지 않았을 때 이 달의 1일로 기본값 설정
        LocalDate startDate = (dateReq.startDate() != null)
                ? dateReq.startDate()
                : LocalDate.now().withDayOfMonth(1);
        //언제까지 조회 할 지를 들고 오지 않았을 때 오늘 날짜로 기본값 설정
        LocalDate endDate = (dateReq.endDate() != null)
                ? dateReq.endDate()
                : LocalDate.now();

        Page<TransLog> searchPage = transLogRepository.findByUserAndDateBetween(
                user,
                startDate,
                endDate,
                pageable
        );

        return searchPage.map(TransLogResponseDto::from);
    }

    public TransLogResponseDto updateTransLog(User user, Long id, TransLogRequestDto req){
        TransLog loadTransLog = transLogRepository.findByIdAndUser_Id(id,user.getId()).orElseThrow(IllegalArgumentException::new);
        loadTransLog.update(req);
        return TransLogResponseDto.from(loadTransLog);
    }

    public void deleteTransLog(User user, Long logId) {
        // 1. 내 거래 내역 중에서 해당 id를 가진 데이터 조회 (타인의 내역 삭제 방지!)
        TransLog loadTransLog = transLogRepository.findByIdAndUser_Id(logId, user.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 거래 내역을 찾을 수 없거나 삭제 권한이 없습니다."));

        // 2. 삭제 실행
        transLogRepository.delete(loadTransLog);
    }
}
