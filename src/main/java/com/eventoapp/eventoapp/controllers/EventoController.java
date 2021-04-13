package com.eventoapp.eventoapp.controllers;

import com.eventoapp.eventoapp.model.Evento;
import com.eventoapp.eventoapp.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EventoController {

    @Autowired
    private EventoRepository eventoRepository;

    @RequestMapping(value ="/cadastrarEvento", method = RequestMethod.GET)
    public String form() {

        return "evento/formEvento";
    }

    @RequestMapping(value ="/cadastrarEvento", method = RequestMethod.POST)
    public String form(Evento evento) {

        eventoRepository.save(evento);

        return "redirect:/cadastrarEvento";
    }

    @RequestMapping("/eventos")
    public ModelAndView lsitaEventos() {
        ModelAndView modelAndView = new ModelAndView("index");
        Iterable<Evento> eventos = eventoRepository.findAll();
        modelAndView.addObject("eventos", eventos);

        return modelAndView;
    }

    @RequestMapping("/{codigo}")
    public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo) {
        Evento evento = eventoRepository.findByCodigo(codigo);
        ModelAndView modelAndView = new ModelAndView("evento/detalhesEvento");
        modelAndView.addObject("evento", evento);
        System.out.println("evento" + evento);

        return modelAndView;
    }
}
