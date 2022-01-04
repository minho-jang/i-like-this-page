package link.iltp.integration;

import link.iltp.common.dto.LikeResponseDto;
import org.junit.jupiter.api.RepeatedTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class Scenario3 extends ScenarioBase {
	private static final int THREAD_POOL_SIZE = 20;
	private static final int USER_NUM = 100;
	private static final String TEST_URL = "localhost/test/1/";
	private static final String SCENE_0 = "0. " + USER_NUM + "명이 동시에 URL [" + TEST_URL + "]에 접속하려고 한다.";
	private static final String SCENE_1 = "1. " + USER_NUM + "명이 동시에 URL [" + TEST_URL + "]의 좋아요 개수를 가져온다.";
	private static final String SCENE_2 = "2. " + USER_NUM + "명이 동시에 URL [" + TEST_URL + "]의 좋아요를 추가한 후 취소한다.";
	private static final String SCENE_3 = "3. URL [" + TEST_URL + "]의 좋아요 개수가 초기값과 같은지 확인한다.";
	private static final String SCENE_END = "시나리오 #3 끝.";

	@RepeatedTest(3)
	public void scenario_3() throws Exception {
		printDescription(SCENE_0);
		init();

		printDescription(SCENE_1);
		concurrentlyGetLikeOf();

		printDescription(SCENE_2);
		concurrentlyAddLikeAndCancelLike();

		printDescription(SCENE_3);
		checkEqualToInitValue();

		printDescription(SCENE_END);
	}

	private List<User> users;
	private long initialLikeCount;

	private void init() throws Exception {
		ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
		users = new ArrayList<>();

		for (int i = 0; i < USER_NUM; i++) {
			final String newToken = JWT_AUTH_PREFIX + jwtTokenProvider.generateNewToken();
			User user = new User(executorService, newToken);
			users.add(user);
		}

		initialLikeCount = getLikeCount();
	}

	private long getLikeCount() throws Exception {
		String token = JWT_AUTH_PREFIX + jwtTokenProvider.generateNewToken();
		LikeResponseDto likeResponseDto = GET_like(TEST_URL, token);

		printDescription("getLikeCount() = " + likeResponseDto.getLikeCount());
		return likeResponseDto.getLikeCount();
	}

	private void concurrentlyGetLikeOf() {
		final List<CompletableFuture<Void>> futures = users.stream()
				.map(u -> u.callGetLikeAsync(TEST_URL))
				.collect(Collectors.toList());

		final CompletableFuture<Void> allFuture = CompletableFuture
				.allOf(futures.toArray(CompletableFuture[]::new))
				.exceptionally(e -> {
					throw new RuntimeException("Failed to call get like of " + TEST_URL + " concurrently");
				});

		allFuture.join();
	}

	private void concurrentlyAddLikeAndCancelLike() {
		final List<CompletableFuture<Void>> futures = users.stream()
				.map(u -> u.callAddLikeThenCancelLike(TEST_URL))
				.collect(Collectors.toList());

		final CompletableFuture<Void> allFuture = CompletableFuture
				.allOf(futures.toArray(CompletableFuture[]::new))
				.exceptionally(e -> {
					throw new RuntimeException("Failed to call add and cancel like of " + TEST_URL + " concurrently");
				});

		allFuture.join();
	}

	private void checkEqualToInitValue() throws Exception {
		long nowLikeCount = getLikeCount();
		assertThat(nowLikeCount, is(equalTo(initialLikeCount)));
	}

	class User {
		private final ExecutorService executorService;
		private final String token;

		User(ExecutorService executorService, String token) {
			this.executorService = executorService;
			this.token = token;
		}

		public CompletableFuture<Void> callGetLikeAsync(String url) {
			return CompletableFuture.runAsync(() -> {
						try {
							GET_like(TEST_URL, token);
						} catch (Exception e) {
							throw new IllegalStateException("Failed call GET /api/v1/like", e);
						}
					}, executorService)
					.exceptionally(e -> {
						throw new IllegalStateException("Failed to get like of " + url, e);
					});
		}

		public CompletableFuture<Void> callAddLikeThenCancelLike(String url) {
			return CompletableFuture.runAsync(() -> {
						try {
							POST_like(url, token);
							DELETE_like(url, token);
						} catch (Exception e) {
							throw new IllegalStateException("Failed to call POST and DELETE /api/v1/like", e);
						}
					}, executorService)
					.exceptionally(e -> {
						throw new IllegalStateException("Failed to add and cancel like of " + url, e);
					});
		}
	}
}
