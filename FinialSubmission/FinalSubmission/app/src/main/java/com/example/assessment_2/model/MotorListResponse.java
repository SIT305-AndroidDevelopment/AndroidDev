package com.example.assessment_2.model;

import com.example.assessment_2.base.BaseResponse;

import java.util.ArrayList;
import java.util.List;

public class MotorListResponse extends BaseResponse {
  public List<MotorItemDto> data = new ArrayList<>();
}
