package com.fiap.globalsolution.controller;

import com.fiap.globalsolution.dto.TrilhaRequest;
import com.fiap.globalsolution.dto.TrilhaResponse;
import com.fiap.globalsolution.service.TrilhaService;
import com.fiap.globalsolution.service.CompetenciaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controller para interface web de Trilhas (Thymeleaf)
 */
@Controller
@RequestMapping("/web/trilhas")
public class TrilhaViewController {

    private final TrilhaService trilhaService;
    private final CompetenciaService competenciaService;

    public TrilhaViewController(TrilhaService trilhaService, CompetenciaService competenciaService) {
        this.trilhaService = trilhaService;
        this.competenciaService = competenciaService;
    }

    /**
     * GET /web/trilhas - Lista todas as trilhas
     */
    @GetMapping
    public String list(Model model) {
        List<TrilhaResponse> trilhas = trilhaService.findAll();
        model.addAttribute("trilhas", trilhas);
        model.addAttribute("pageTitle", "Lista de Trilhas");
        return "trilhas/list";
    }

    /**
     * GET /web/trilhas/{id} - Visualiza trilha
     */
    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return trilhaService.findById(id)
                .map(trilha -> {
                    model.addAttribute("trilha", trilha);
                    model.addAttribute("pageTitle", "Detalhes da Trilha");
                    return "trilhas/view";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Trilha não encontrada!");
                    return "redirect:/web/trilhas";
                });
    }

    /**
     * GET /web/trilhas/new - Exibe formulário de criação
     */
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("trilha", new TrilhaRequest(null, null, null, null, null, null));
        model.addAttribute("competencias", competenciaService.findAll());
        model.addAttribute("pageTitle", "Nova Trilha");
        model.addAttribute("isEdit", false);
        return "trilhas/form";
    }

    /**
     * POST /web/trilhas - Cria nova trilha
     */
    @PostMapping
    public String create(@Valid @ModelAttribute("trilha") TrilhaRequest request,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("competencias", competenciaService.findAll());
            model.addAttribute("pageTitle", "Nova Trilha");
            model.addAttribute("isEdit", false);
            return "trilhas/form";
        }

        try {
            TrilhaResponse created = trilhaService.create(request);
            redirectAttributes.addFlashAttribute("success", "Trilha criada com sucesso!");
            return "redirect:/web/trilhas/" + created.id();
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/web/trilhas";
        }
    }

    /**
     * GET /web/trilhas/{id}/edit - Exibe formulário de edição
     */
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return trilhaService.findById(id)
                .map(trilha -> {
                    TrilhaRequest request = new TrilhaRequest(
                            trilha.nome(),
                            trilha.descricao(),
                            trilha.nivel(),
                            trilha.cargaHoraria(),
                            trilha.focoPrincipal(),
                            trilha.competencias().stream()
                                    .map(CompetenciaResponse::id)
                                    .collect(java.util.stream.Collectors.toSet())
                    );
                    model.addAttribute("trilha", request);
                    model.addAttribute("trilhaId", id);
                    model.addAttribute("competencias", competenciaService.findAll());
                    model.addAttribute("pageTitle", "Editar Trilha");
                    model.addAttribute("isEdit", true);
                    return "trilhas/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Trilha não encontrada!");
                    return "redirect:/web/trilhas";
                });
    }

    /**
     * POST /web/trilhas/{id} - Atualiza trilha
     */
    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("trilha") TrilhaRequest request,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("trilhaId", id);
            model.addAttribute("competencias", competenciaService.findAll());
            model.addAttribute("pageTitle", "Editar Trilha");
            model.addAttribute("isEdit", true);
            return "trilhas/form";
        }

        try {
            return trilhaService.update(id, request)
                    .map(updated -> {
                        redirectAttributes.addFlashAttribute("success", "Trilha atualizada com sucesso!");
                        return "redirect:/web/trilhas/" + id;
                    })
                    .orElseGet(() -> {
                        redirectAttributes.addFlashAttribute("error", "Trilha não encontrada!");
                        return "redirect:/web/trilhas";
                    });
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/web/trilhas";
        }
    }

    /**
     * POST /web/trilhas/{id}/delete - Deleta trilha
     */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (trilhaService.delete(id)) {
            redirectAttributes.addFlashAttribute("success", "Trilha deletada com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Trilha não encontrada!");
        }
        return "redirect:/web/trilhas";
    }
}