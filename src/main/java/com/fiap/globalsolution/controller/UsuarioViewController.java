package com.fiap.globalsolution.controller;

import com.fiap.globalsolution.dto.UsuarioRequest;
import com.fiap.globalsolution.dto.UsuarioResponse;
import com.fiap.globalsolution.exception.DuplicateEntityException;
import com.fiap.globalsolution.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controller Web (Thymeleaf) para gerenciamento de Usuários
 */
@Controller
@RequestMapping("/web/usuarios")
public class UsuarioViewController {

    private final UsuarioService service;

    public UsuarioViewController(UsuarioService service) {
        this.service = service;
    }

    /**
     * GET /web/usuarios - Lista todos os usuários
     */
    @GetMapping
    public String list(Model model) {
        List<UsuarioResponse> usuarios = service.findAll();
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("pageTitle", "Lista de Usuários");
        return "usuarios/list";
    }

    /**
     * GET /web/usuarios/{id} - Visualiza detalhes de um usuário
     */
    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return service.findById(id)
                .map(usuario -> {
                    model.addAttribute("usuario", usuario);
                    model.addAttribute("pageTitle", "Detalhes do Usuário");
                    return "usuarios/view";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Usuário não encontrado!");
                    return "redirect:/web/usuarios";
                });
    }

    /**
     * GET /web/usuarios/new - Exibe formulário de novo usuário
     */
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("usuario", new UsuarioRequest(null, null, null, null, null));
        model.addAttribute("pageTitle", "Novo Usuário");
        model.addAttribute("isEdit", false);
        return "usuarios/form";
    }

    /**
     * POST /web/usuarios - Cria novo usuário
     */
    @PostMapping
    public String create(@Valid @ModelAttribute("usuario") UsuarioRequest request,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Novo Usuário");
            model.addAttribute("isEdit", false);
            return "usuarios/form";
        }

        try {
            UsuarioResponse created = service.create(request);
            redirectAttributes.addFlashAttribute("success", "Usuário criado com sucesso!");
            return "redirect:/web/usuarios/" + created.id();
        } catch (DuplicateEntityException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/web/usuarios";
        }
    }

    /**
     * GET /web/usuarios/{id}/edit - Exibe formulário de edição
     */
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return service.findById(id)
                .map(usuario -> {
                    UsuarioRequest request = new UsuarioRequest(
                            usuario.nome(),
                            usuario.email(),
                            usuario.areaAtuacao(),
                            usuario.nivelCarreira(),
                            usuario.dataCadastro()
                    );
                    model.addAttribute("usuario", request);
                    model.addAttribute("usuarioId", id);
                    model.addAttribute("pageTitle", "Editar Usuário");
                    model.addAttribute("isEdit", true);
                    return "usuarios/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Usuário não encontrado!");
                    return "redirect:/web/usuarios";
                });
    }

    /**
     * POST /web/usuarios/{id} - Atualiza usuário existente
     */
    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("usuario") UsuarioRequest request,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("usuarioId", id);
            model.addAttribute("pageTitle", "Editar Usuário");
            model.addAttribute("isEdit", true);
            return "usuarios/form";
        }

        try {
            return service.update(id, request)
                    .map(updated -> {
                        redirectAttributes.addFlashAttribute("success", "Usuário atualizado com sucesso!");
                        return "redirect:/web/usuarios/" + id;
                    })
                    .orElseGet(() -> {
                        redirectAttributes.addFlashAttribute("error", "Usuário não encontrado!");
                        return "redirect:/web/usuarios";
                    });
        } catch (DuplicateEntityException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/web/usuarios";
        }
    }

    /**
     * POST /web/usuarios/{id}/delete - Deleta um usuário
     */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (service.delete(id)) {
            redirectAttributes.addFlashAttribute("success", "Usuário deletado com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Usuário não encontrado!");
        }
        return "redirect:/web/usuarios";
    }
}