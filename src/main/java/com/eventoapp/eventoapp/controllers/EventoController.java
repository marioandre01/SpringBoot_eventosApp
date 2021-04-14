package com.eventoapp.eventoapp.controllers;

import com.eventoapp.eventoapp.model.Convidado;
import com.eventoapp.eventoapp.model.Evento;
import com.eventoapp.eventoapp.repository.ConvidadoRepository;
import com.eventoapp.eventoapp.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.Binding;
import javax.validation.Valid;

@Controller
public class EventoController {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private ConvidadoRepository convidadoRepository;

    @RequestMapping(value ="/cadastrarEvento", method = RequestMethod.GET)
    public String form() {

        return "evento/formEvento";
    }

    @RequestMapping(value ="/cadastrarEvento", method = RequestMethod.POST)
    public String form( @Valid Evento evento, BindingResult resul, RedirectAttributes attributes) {
        if(resul.hasErrors()){
            //attributes.addFlashAttribute redicionará a mensagem na view para o usuario
            attributes.addFlashAttribute("mensagem", "Verifique os campos!");

            return "redirect:/cadastrarEvento";
        }
        eventoRepository.save(evento);
        attributes.addFlashAttribute("mensagem", "Evento cadastrado com sucesso!");

        return "redirect:/cadastrarEvento";
    }

    @RequestMapping("/eventos")
    public ModelAndView lsitaEventos() {
        ModelAndView modelAndView = new ModelAndView("index");
        Iterable<Evento> eventos = eventoRepository.findAll();
        modelAndView.addObject("eventos", eventos);

        return modelAndView;
    }

    @RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
    public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo) {
        Evento evento = eventoRepository.findByCodigo(codigo);
        ModelAndView modelAndView = new ModelAndView("evento/detalhesEvento");
        modelAndView.addObject("evento", evento);

        Iterable<Convidado> convidados = convidadoRepository.findByEvento(evento);
        modelAndView.addObject("convidados", convidados); //enviar a lista para a view "evento/detalhesEvento"

        return modelAndView;
    }

    @RequestMapping("/deletarEvento")
    public String deletarEvento(long codigo){
        Evento evento = eventoRepository.findByCodigo(codigo);
        eventoRepository.delete(evento);

        return "redirect:/eventos";
    }

    //Com o @Valid vai ser validado o convidado, anotado com @NotEmpty em model/Convidado
    @RequestMapping(value = "/{codigo}", method = RequestMethod.POST)
    public String detalhesEventoPost(@PathVariable("codigo") long codigo, @Valid Convidado convidado, BindingResult resul, RedirectAttributes attributes) {
        //antes de salvar no banco vai fazer a verificação pra ver se tem erros
        // se results tiver algum erro
        //se tiver algum erro no formulario, como rg em branco
        if(resul.hasErrors()){
            //attributes.addFlashAttribute redicionará a mensagem na view para o usuario
            attributes.addFlashAttribute("mensagem", "Verifique os campos!");

            return "redirect:/{codigo}"; //redirecionar para a pagina do codigo
        }

        Evento evento = eventoRepository.findByCodigo(codigo);
        convidado.setEvento(evento);
        convidadoRepository.save(convidado);
        attributes.addFlashAttribute("mensagem", "Convidado adicionado com sucesso!");

        return "redirect:/{codigo}";
    }

    @RequestMapping("/deletarConvidado")
    public String deletarConvidado(String rg){
        Convidado convidado = convidadoRepository.findByRg(rg);
        convidadoRepository.delete(convidado);

        Evento evento = convidado.getEvento();
        long codigoLong = evento.getCodigo();
        String codigo = "" + codigoLong;

        return "redirect:/" + codigo;
    }
}
