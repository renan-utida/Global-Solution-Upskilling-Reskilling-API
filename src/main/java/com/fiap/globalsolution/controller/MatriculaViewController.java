package com.fiap.globalsolution.controller;

import com.fiap.globalsolution.dto.MatriculaRequest;
import com.fiap.globalsolution.dto.MatriculaResponse;
import com.fiap.globalsolution.exception.DuplicateEntityException;
import com.fiap.globalsolution.exception.TrilhaNaoEncontradaException;
import com.fiap.globalsolution.exception.UsuarioNaoEncontradoException;
import com.fiap.globalsolution.service.MatriculaService;
import com.fiap.globalsolution.service.TrilhaService;
import com.fiap.globalsolution.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controller Web (Thymeleaf) para gerenciamento de Matrículas
 */
@Controller
@RequestMapping("/web/matriculas")
public class MatriculaViewController {

    private final MatriculaService service;
    private final UsuarioService usuarioService;
    private final TrilhaService trilhaService;

    public MatriculaViewController(MatriculaService service,
                                   UsuarioService usuarioService,
                                   TrilhaService trilhaService) {
        this.service = service;
        this.usuarioService = usuarioService;
        this.trilhaService = trilhaService;
    }

    /**
     * GET /web/matriculas - Lista todas as matrículas
     */
    @GetMapping
    public String list(Model model) {
        List<MatriculaResponse> matriculas = service.findAll();
        model.addAttribute("matriculas", matriculas);
        model.addAttribute("pageTitle", "Lista de Matrículas");
        return "matriculas/list";
    }

    /**
     * GET /web/matriculas/{id} - Visualiza detalhes de uma matrícula
     */
    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return service.findById(id)
                .map(matricula -> {
                    model.addAttribute("matricula", matricula);
                    model.addAttribute("pageTitle", "Detalhes da Matrícula");
                    return "matriculas/view";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Matrícula não encontrada!");
                    return "redirect:/web/matriculas";
                });
    }

    /**
     * GET /web/matriculas/new - Exibe formulário de nova matrícula
     */
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("matricula", new MatriculaRequest(null, null, null, null));
        model.addAttribute("usuarios", usuarioService.findAll());
        model.addAttribute("trilhas", trilhaService.findAll());
        model.addAttribute("pageTitle", "Nova Matrícula");
        model.addAttribute("isEdit", false);
        return "matriculas/form";
    }

    /**
     * POST /web/matriculas - Cria nova matrícula
     */
    @PostMapping
    public String create(@Valid @ModelAttribute("matricula") MatriculaRequest request,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("usuarios", usuarioService.findAll());
            model.addAttribute("trilhas", trilhaService.findAll());
            model.addAttribute("pageTitle", "Nova Matrícula");
            model.addAttribute("isEdit", false);
            return "matriculas/form";
        }

        try {
            MatriculaResponse created = service.create(request);
            redirectAttributes.addFlashAttribute("success", "Matrícula criada com sucesso!");
            return "redirect:/web/matriculas/" + created.id();
        } catch (UsuarioNaoEncontradoException | TrilhaNaoEncontradaException | DuplicateEntityException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/web/matriculas";
        }
    }

    /**
     * GET /web/matriculas/{id}/edit - Exibe formulário de edição
     */
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return service.findById(id)
                .map(matricula -> {
                    MatriculaRequest request = new MatriculaRequest(
                            matricula.usuario().id(),
                            matricula.trilha().id(),
                            matricula.dataInscricao(),
                            matricula.status()
                    );
                    model.addAttribute("matricula", request);
                    model.addAttribute("matriculaId", id);
                    model.addAttribute("usuarios", usuarioService.findAll());
                    model.addAttribute("trilhas", trilhaService.findAll());
                    model.addAttribute("pageTitle", "Editar Matrícula");
                    model.addAttribute("isEdit", true);
                    return "matriculas/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Matrícula não encontrada!");
                    return "redirect:/web/matriculas";
                });
    }

    /**
     * POST /web/matriculas/{id} - Atualiza matrícula existente
     */
    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("matricula") MatriculaRequest request,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("matriculaId", id);
            model.addAttribute("usuarios", usuarioService.findAll());
            model.addAttribute("trilhas", trilhaService.findAll());
            model.addAttribute("pageTitle", "Editar Matrícula");
            model.addAttribute("isEdit", true);
            return "matriculas/form";
        }

        try {
            return service.update(id, request)
                    .map(updated -> {
                        redirectAttributes.addFlashAttribute("success", "Matrícula atualizada com sucesso!");
                        return "redirect:/web/matriculas/" + id;
                    })
                    .orElseGet(() -> {
                        redirectAttributes.addFlashAttribute("error", "Matrícula não encontrada!");
                        return "redirect:/web/matriculas";
                    });
        } catch (UsuarioNaoEncontradoException | TrilhaNaoEncontradaException | DuplicateEntityException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/web/matriculas";
        }
    }

    /**
     * POST /web/matriculas/{id}/delete - Deleta uma matrícula
     */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (service.delete(id)) {
            redirectAttributes.addFlashAttribute("success", "Matrícula deletada com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Matrícula não encontrada!");
        }
        return "redirect:/web/matriculas";
    }
}