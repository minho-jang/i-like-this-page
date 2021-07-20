package src.main.java.com.minhojang.ilikethispagebackend.api;

import com.minhojang.ilikethispagebackend.models.Like;
import com.minhojang.ilikethispagebackend.services.LikeService;
import com.minhojang.ilikethispagebackend.utils.IpAddressUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class MainRestController {

  @Autowired
  LikeService likeService;

  @GetMapping("/like")
  public ApiResult like() {
    return new ApiResult(null, "SUCCESS");
  }

  @PostMapping("/like")
  public ApiResult likeSave() {
    Like like = new Like(null, // id (PK)
        IpAddressUtils.ipv4ToLong("127.0.0.1"), null, // ip_address_v6
        "http://localhost:8080/", null // create_at
    );
    likeService.save(like);
    return new ApiResult(null, "SUCCESS");
  }
}

// TODO 다른 코드 참조하여 ApiResult 클래스 새로 만들기
class ApiResult {
  String error;
  String data;

  public ApiResult(String error, String data) {
    this.error = error;
    this.data = data;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }
}