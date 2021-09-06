package com.ssafy.spotlive.api.request.timetable;

import com.ssafy.spotlive.db.entity.ShowInfo;
import com.ssafy.spotlive.db.entity.Timetable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @FileName : TimetableInsertPostReq
 * @작성자 : 금아현
 * @Class 설명 : 공연 정보 등록 API 요청에 필요한 리퀘스트 바디 정의
 */
@Getter
@Setter
@ToString
@ApiModel("TimetableInsertPostReq")
public class TimetableInsertPostReq {
    /**
     * @Method Name : toTimetable
     * @작성자 : 금아현
     * @Method 설명 : Timetable DTO를 Entity로 변환
     */
    @ApiModelProperty(name = "dateTime", example = "2222-11-12T16:34:30.388")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime dateTime;

    public Timetable toTimetable(ShowInfo showInfo){
        Timetable timetable = new Timetable();
        timetable.setDateTime(this.dateTime);
        timetable.setShowInfo(showInfo);
        return timetable;
    }

}
