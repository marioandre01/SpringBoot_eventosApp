package com.eventoapp.eventoapp.controllers;

import com.eventoapp.eventoapp.model.Convidado;
import com.eventoapp.eventoapp.model.Evento;
import com.eventoapp.eventoapp.repository.ConvidadoRepository;
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

    @Autowired
    private ConvidadoRepository convidadoRepository;

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

    @RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
    public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo) {
        Evento evento = eventoRepository.findByCodigo(codigo);
        ModelAndView modelAndView = new ModelAndView("evento/detalhesEvento");
        modelAndView.addObject("evento", evento);

        Iterable<Convidado> convidados = convidadoRepository.findByEvento(evento);
        modelAndView.addObject("convidados", convidados); //enviar a lista para a view "evento/detalhesEvento"

        return modelAndView;
    }

    @RequestMapping(value = "/{codigo}", method = RequestMethod.POST)
    public String detalhesEventoPost(@PathVariable("codigo") long codigo, Convidado convidado) {
        Evento evento = eventoRepository.findByCodigo(codigo);
        convidado.setEvento(evento);
        convidadoRepository.save(convidado);

        return "redirect:/{codigo}";
    }
}
