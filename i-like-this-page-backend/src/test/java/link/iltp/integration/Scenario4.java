package link.iltp.integration;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class Scenario4 extends ScenarioBase {
	private static final String SCENE_1 = "프론트엔드를 번들링한 자바스크립트 파일이 있는지 확인한다.";
	private static final String SCENE_END = "시나리오 #4 끝.";

	@Test
	public void scenario_4() throws Exception {
		printDescription(SCENE_1);
		checkIltpBundleJs();

		printDescription(SCENE_END);
	}

	private void checkIltpBundleJs() throws Exception {
		mockMvc.perform(get("/bundle/iltp.bundle.min.js"))
				.andExpect(status().isOk())
				.andExpect(header().stringValues("Content-Type", contains("application/javascript")));
	}
}
