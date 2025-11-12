package com.fiap.globalsolution.controller;

import com.fiap.globalsolution.dto.TrilhaRequest;
import com.fiap.globalsolution.dto.TrilhaResponse;
import com.fiap.globalsolution.exception.DuplicateEntityException;
import com.fiap.globalsolution.service.TrilhaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controller Web (Thymeleaf) para gerenciamento de Trilhas
 */
@Controller
@RequestMapping("/web/trilhas")
public class TrilhaViewController {

    private final TrilhaService service;

    public TrilhaViewController(TrilhaService service) {
        this.service = service;
    }

    /**
     * GET /web/trilhas - Lista todas as trilhas
     */
    @GetMapping
    public String list(Model model) {
        List<TrilhaResponse> trilhas = service.findAll();
        model.addAttribute("trilhas", trilhas);
        model.addAttribute("pageTitle", "Lista de Trilhas");
        return "trilhas/list";
    }

    /**
     * GET /web/trilhas/{id} - Visualiza detalhes de uma trilha
     */
    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return service.findById(id)
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
     * GET /web/trilhas/new - Exibe formulário de nova trilha
     */
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("trilha", new TrilhaRequest(null, null, null, null, null));
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
            model.addAttribute("pageTitle", "Nova Trilha");
            model.addAttribute("isEdit", false);
            return "trilhas/form";
        }

        try {
            TrilhaResponse created = service.create(request);
            redirectAttributes.addFlashAttribute("success", "Trilha criada com sucesso!");
            return "redirect:/web/trilhas/" + created.id();
        } catch (DuplicateEntityException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/web/trilhas";
        }
    }

    /**
     * GET /web/trilhas/{id}/edit - Exibe formulário de edição
     */
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return service.findById(id)
                .map(trilha -> {
                    TrilhaRequest request = new TrilhaRequest(
                            trilha.nome(),
                            trilha.descricao(),
                            trilha.nivel(),
                            trilha.cargaHoraria(),
                            trilha.focoPrincipal()
                    );
                    model.addAttribute("trilha", request);
                    model.addAttribute("trilhaId", id);
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
     * POST /web/trilhas/{id} - Atualiza trilha existente
     */
    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("trilha") TrilhaRequest request,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("trilhaId", id);
            model.addAttribute("pageTitle", "Editar Trilha");
            model.addAttribute("isEdit", true);
            return "trilhas/form";
        }

        try {
            return service.update(id, request)
                    .map(updated -> {
                        redirectAttributes.addFlashAttribute("success", "Trilha atualizada com sucesso!");
                        return "redirect:/web/trilhas/" + id;
                    })
                    .orElseGet(() -> {
                        redirectAttributes.addFlashAttribute("error", "Trilha não encontrada!");
                        return "redirect:/web/trilhas";
                    });
        } catch (DuplicateEntityException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/web/trilhas";
        }
    }

    /**
     * POST /web/trilhas/{id}/delete - Deleta uma trilha
     */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (service.delete(id)) {
            redirectAttributes.addFlashAttribute("success", "Trilha deletada com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Trilha não encontrada!");
        }
        return "redirect:/web/trilhas";
    }
}