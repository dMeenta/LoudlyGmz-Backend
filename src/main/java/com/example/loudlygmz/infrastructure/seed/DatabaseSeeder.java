package com.example.loudlygmz.infrastructure.seed;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import com.example.loudlygmz.domain.model.Category;
import com.example.loudlygmz.domain.model.Game;
import com.example.loudlygmz.domain.model.GameAssets;
import com.example.loudlygmz.domain.repository.ICategoryRepository;
import com.example.loudlygmz.domain.repository.IGameRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final IGameRepository gameDAO;
    private final ICategoryRepository categoryDAO;

    private static final Logger logger = LoggerFactory.getLogger(DatabaseSeeder.class);

    @Override
    public void run(String... args) throws Exception {

        if(categoryDAO.count() > 0 && gameDAO.count() > 0){
            logger.warn(
                "\n|======================================================|\n" +
                "|       SEEDING ABORTADO PORQUE YA EXISTEN DATOS       |\n"+
                "|======================================================|");
            return;
        }

        Category action = new Category("Acción", "Juegos centrados en la habilidad, reflejos y combate rápido, donde la adrenalina y la intensidad son protagonistas.");
        Category adventure = new Category("Aventura", "Experiencias inmersivas que combinan exploración, narrativa y resolución de acertijos en mundos variados.");
        Category role = new Category("Rol", "Juegos donde el jugador asume el papel de un personaje, desarrollando habilidades y tomando decisiones que afectan la historia.");
        Category twoD = new Category("2D", "Juegos con gráficos bidimensionales, a menudo estilizados, que priorizan la jugabilidad clásica o artística.");
        Category threeD = new Category("3D", "Juegos con entornos tridimensionales, que ofrecen una sensación de profundidad y exploración en un espacio más realista.");
        Category metroidvania = new Category("Metroidvania", "Juegos de exploración no lineal, inspirados en Metroid y Castlevania, con progresión basada en habilidades y desbloqueos.");
        Category platform = new Category("Plataformas", "Juegos centrados en saltos, obstáculos y navegación a través de niveles desafiantes y entretenidos.");
        Category moba = new Category("MOBA", "Multiplayer Online Battle Arena: juegos estratégicos donde equipos compiten en mapas con objetivos específicos.");
        Category tacticalShooter = new Category("Tactical Shooter", "Juegos de disparos enfocados en estrategia, trabajo en equipo y una jugabilidad realista y táctica.");

        categoryDAO.saveAll(List.of(action, adventure, role, twoD, threeD, metroidvania, platform, moba, tacticalShooter));

        Game g1 = Game.builder()
        .name("League of Legends")
        .description("League of Legends es un juego de estrategia por equipos en el que dos equipos de cinco campeones se enfrentan para ver quién destruye antes la base del otro. Elige de una aplia gama de campeones para realizar jugadas épicas, asesinar rivales y derribar torretas para alzarte con la victoria.")
        .release_date(LocalDate.parse("2009-10-27"))
        .developer("Riot Games")
        .assets(new GameAssets("", "", "", ""))
        .categories(List.of(action, role, moba))
        .build();

        Game g2 = Game.builder()
        .name("Valorant")
        .description("Un juego de disparos táctico 5v5 basado en personajes.")
        .release_date(LocalDate.parse("2020-06-02"))
        .developer("Riot Games")
        .assets(new GameAssets("", "", "", ""))
        .categories(List.of(action, threeD, tacticalShooter))
        .build();

        Game g3 = Game.builder()
        .name("Hollow Knight")
        .description("¡Forja tu propio camino en Hollow Knight! Una aventura épica a través de un vasto reino de insectos y héroes que se encuentra en ruinas. Explora cavernas tortuosas, combate contra criaturas corrompidas y entabla amistad con extraños insectos, todo en un estilo clásico en 2D dibujado a mano.")
        .release_date(LocalDate.parse("2017-02-24"))
        .developer("Team Cherry")
        .assets(new GameAssets("", "", "", ""))
        .categories(List.of(action, adventure, twoD, metroidvania, platform))
        .build();
        
        Game g4 = Game.builder()
        .name("Kingdom Hearts")
        .description("Kingdom Hearts es el resultado de una colaboración entre Square y The Walt Disney Company.")
        .release_date(LocalDate.parse("2002-03-28"))
        .developer("Squaresoft")
        .assets(new GameAssets("", "", "", ""))
        .categories(List.of(action, adventure, role, threeD))
        .build();

        Game g5 = Game.builder()
        .name("Kingdom Hearts Chain Of Memories")
        .description("Kingdom Hearts: Chain Of Memories retoma la historia donde acaba Kingdom Hearts 1 y los primeros 50-60 días de Kingdom Hearts 358/2 Days.")
        .release_date(LocalDate.parse("2004-11-11"))
        .developer("Square Enix")
        .assets(new GameAssets("", "", "", ""))
        .categories(List.of(action, adventure, role, threeD))
        .build();

        Game g6 = Game.builder()
        .name("Kingdom Hearts 2")
        .description("Kingdom Hearts 2 es la secuela directa de Kingdom Hearts: Chain of Memories.")
        .release_date(LocalDate.parse("2005-12-22"))
        .developer("Square Enix")
        .assets(new GameAssets("", "", "", ""))
        .categories(List.of(action, adventure, role, threeD))
        .build();

        Game g7 = Game.builder()
        .name("Warface")
        .description("Un FPS táctico con modos cooperativos y competitivos, ofreciendo misiones dinámicas y múltiples clases de personajes.")
        .release_date(LocalDate.parse("2013-10-21"))
        .developer("Crytek")
        .assets(new GameAssets("", "", "", ""))
        .categories(List.of(action, tacticalShooter, threeD))
        .build();

        Game g8 = Game.builder()
        .name("Apex Legends")
        .description("Un shooter táctico basado en escuadrones con héroes únicos, ambientado en el universo de Titanfall.")
        .release_date(LocalDate.parse("2019-02-04"))
        .developer("Respawn Entertainment")
        .assets(new GameAssets("", "", "", ""))
        .categories(List.of(action, moba, threeD))
        .build();

        Game g9 = Game.builder()
        .name("Enlisted")
        .description("Un shooter táctico ambientado en la Segunda Guerra Mundial donde los jugadores controlan escuadrones de soldados en combates históricos.")
        .release_date(LocalDate.parse("2021-04-08"))
        .developer("Darkflow Software")
        .assets(new GameAssets("", "", "", ""))
        .categories(List.of(action, threeD))
        .build();

        Game g10 = Game.builder()
        .name("Super Mario Odyssey")
        .description("Mario emprende una aventura en un mundo de plataformas 3D para rescatar a la princesa Peach usando su nuevo compañero, Cappy.")
        .release_date(LocalDate.parse("2017-10-27"))
        .developer("Nintendo")
        .assets(new GameAssets("", "", "", ""))
        .categories(List.of(adventure, threeD, platform))
        .build();

        Game g11 = Game.builder()
        .name("Ori and the Blind Forest")
        .description("Un juego de plataformas que combina una hermosa narrativa con desafiantes mecánicas mientras Ori intenta salvar su bosque.")
        .release_date(LocalDate.parse("2015-03-11"))
        .developer("Moon Studios")
        .assets(new GameAssets("", "", "", ""))
        .categories(List.of(adventure, twoD, metroidvania))
        .build();

        Game g12 = Game.builder()
        .name("Celeste")
        .description("Ayuda a Madeline a escalar la montaña Celeste enfrentando desafiantes niveles de plataformas y sus propios miedos.")
        .release_date(LocalDate.parse("2018-01-25"))
        .developer("Maddy Makes Games")
        .assets(new GameAssets("", "", "", ""))
        .categories(List.of(adventure, twoD, metroidvania))
        .build();

        Game g13 = Game.builder()
        .name("The Witcher 3 - Wild Hunt")
        .description("Un RPG de mundo abierto donde Geralt de Rivia busca a su hija adoptiva mientras enfrenta monstruos y decisiones morales.")
        .release_date(LocalDate.parse("2015-05-19"))
        .developer("CD Projekt Red")
        .assets(new GameAssets("", "", "", ""))
        .categories(List.of(action, role))
        .build();

        Game g14 = Game.builder()
        .name("Elden Ring")
        .description("Un juego de rol de acción ambientado en un mundo abierto, con una historia creada por Hidetaka Miyazaki y George R.R. Martin.")
        .release_date(LocalDate.parse("2022-02-25"))
        .developer("FromSoftware")
        .assets(new GameAssets("", "", "", ""))
        .categories(List.of(action, role, threeD))
        .build();

        Game g15 = Game.builder()
        .name("Final Fantasy XV")
        .description("Un juego de rol épico donde el príncipe Noctis y sus amigos intentan recuperar su reino de las fuerzas enemigas.")
        .release_date(LocalDate.parse("2016-11-29"))
        .developer("Square Enix")
        .assets(new GameAssets("", "", "", ""))
        .categories(List.of(action, role, threeD))
        .build();
        
        gameDAO.saveAll(List.of(g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12, g13, g14, g15));
    }
}