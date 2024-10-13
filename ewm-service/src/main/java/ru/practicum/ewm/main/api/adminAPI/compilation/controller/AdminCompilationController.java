package ru.practicum.ewm.main.api.adminAPI.compilation.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.main.api.adminAPI.compilation.service.AdminCompilationService;
import ru.practicum.ewm.main.data.dto.compilation.CompilationDto;
import ru.practicum.ewm.main.data.dto.compilation.NewCompilationDto;
import ru.practicum.ewm.main.data.dto.compilation.UpdateCompilationRequest;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/admin/compilations")
public class AdminCompilationController {
    private final AdminCompilationService adminCompilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto addCompilation(@RequestBody @Valid NewCompilationDto newCompilationDto) {
        return adminCompilationService.addCompilation(newCompilationDto);
    }

    @DeleteMapping("{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilationById(@PathVariable @NotNull Long compId) {
        adminCompilationService.deleteCompilationById(compId);
    }

    @PatchMapping("{compId}")
    public CompilationDto updateCompilationById(@PathVariable @NotNull Long compId,
                                                @RequestBody @Valid UpdateCompilationRequest compilationRequest) {
        return adminCompilationService.updateCompilationById(compId, compilationRequest);
    }
}