package ru.practicum.ewm.main.api.publicAPI.compilation.controller;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.main.api.publicAPI.compilation.service.PublicCompilationService;
import ru.practicum.ewm.main.data.constants.DefaultValue;
import ru.practicum.ewm.main.data.dto.compilation.CompilationDto;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/compilations")
public class PublicCompilationController {
    private final PublicCompilationService publicCompilationService;

    @GetMapping
    public List<CompilationDto> getCompilations(@RequestParam(required = false) Boolean pinned,
                                                @PositiveOrZero @RequestParam(defaultValue = DefaultValue.ZERO) Integer from,
                                                @Positive @RequestParam(defaultValue = DefaultValue.SIZE_10) Integer size) {
        return publicCompilationService.getCompilations(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilationById(@PathVariable @NotNull Long compId) {
        return publicCompilationService.getCompilationById(compId);
    }
}