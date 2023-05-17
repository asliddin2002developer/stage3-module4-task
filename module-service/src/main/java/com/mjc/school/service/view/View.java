package com.mjc.school.service.view;
import org.springframework.stereotype.Component;

@Component
public interface View<T, L> {
        void display(T t);
        void displayAll(L l);
}
