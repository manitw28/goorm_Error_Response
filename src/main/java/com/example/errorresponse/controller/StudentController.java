package com.example.errorresponse.controller;

import com.example.errorresponse.entity.ApiResponse;
import com.example.errorresponse.entity.ErrorCode;
import com.example.errorresponse.exception.CustomException;
import com.example.errorresponse.exception.InputRestriction;
import com.example.errorresponse.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    // 1. 이름과 성적을 입력받아 저장하는 API
// POST
    @PostMapping("/student")
    public ApiResponse add(
            @RequestParam("name") String name,
            @RequestParam("grade") int grade
    ) {

//        try {
//            test(name, grade);
//        } catch (Exception e) {
//            throw new CustomException(ErrorCode.BAD_REQUEST, "custom exception", null);
//        }


        if (grade >= 6) {
            throw new CustomException(ErrorCode.BAD_REQUEST, "grade는 6 이상을 입력할 수 없습니다.", new InputRestriction(6));
        }

        return makeResponse(studentService.addStudent(name, grade));
    }


    // 2. 전체 학생을 조회하는 API
    @GetMapping("/students")
    public ApiResponse getAll() {
        return makeResponse(studentService.getAll());
    }


    // 3. 특정 성적을 입력받아 해당 성적의 학생들을 조회
    @GetMapping("/students/{grade}")
    public ApiResponse getGradeStudents(
        @PathVariable("grade") int grade
    ) {
        return makeResponse(studentService.getGradeStudent(grade));
    }



    // 복수의 result를 가지고 있는 경우 응답 내려주기
    public <T> ApiResponse<T> makeResponse(List<T> results) {
        return new ApiResponse<>(results);
    }

    // 단수의 result를 가지고 있는 경우 응답 내려주기
    public <T> ApiResponse<T> makeResponse(T result) {
        return makeResponse(Collections.singletonList(result));
    }

    // Controller가 많아지면 exceptionHandler만 모아 놓은 핸들러 클래스를 따로 만들어서
    // 그 클래스에 @RestControllerAdvice 라는 어노테이션을 걸어주면
    // 매 controller마다 exceptionHandler를 걸어주지 않아도 어드바이스를 통해서 자동으로 걸린다.
    // Spring AOP, JDK Dynamic Proxy와 관련
    @ExceptionHandler(CustomException.class)
    public ApiResponse customExceptionHandler(CustomException customException) {
        return new ApiResponse(customException.getErrorCode().getCode(), customException.getErrorCode().getMessage(), customException.getData());
    }


    // 하위과제 테스트
    public ApiResponse test(String name, int grade) {
        throw new CustomException(ErrorCode.BAD_REQUEST, "grade는 6 이상 입력 불가!", new InputRestriction(6));
    }

}
