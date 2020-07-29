package woowacrew.github.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacrew.github.dto.UserCommitRankDetailResponseDto;
import woowacrew.github.service.GithubCommitService;
import woowacrew.user.dto.UserContext;

import java.util.List;

@RestController
@RequestMapping("/api/github/commit/rank")
public class CommitRankApiController {

    private GithubCommitService githubCommitService;

    public CommitRankApiController(GithubCommitService githubCommitService) {
        this.githubCommitService = githubCommitService;
    }

    @GetMapping
    public ResponseEntity<List<UserCommitRankDetailResponseDto>> getTotalCommitRank() {
        List<UserCommitRankDetailResponseDto> commitRank = githubCommitService.getTotalCommitRank();
        return ResponseEntity.ok(commitRank);
    }

    @GetMapping("/me")
    public ResponseEntity<UserCommitRankDetailResponseDto> getMyCommitRank(UserContext userContext) {
        UserCommitRankDetailResponseDto result = githubCommitService.getLoginUserCommitRank(userContext);
        return ResponseEntity.ok(result);
    }
}
