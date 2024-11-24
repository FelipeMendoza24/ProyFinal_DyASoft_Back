package com.proyfinaldyasoft.inmobiliariosystem.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "MICROA")
public interface MicroAFeign {

    @GetMapping("/cambio")
    String obtenerRespuestaA(@RequestParam Long id, @RequestParam Boolean estado);
}
