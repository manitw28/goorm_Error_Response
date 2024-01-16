package com.example.errorresponse.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class InputRestriction {
    private int maxGrade;
}
