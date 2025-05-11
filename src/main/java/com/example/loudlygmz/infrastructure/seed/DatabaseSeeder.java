package com.example.loudlygmz.infrastructure.seed;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import com.example.loudlygmz.domain.model.Category;
import com.example.loudlygmz.domain.model.Game;
import com.example.loudlygmz.domain.repository.ICategoryRepository;
import com.example.loudlygmz.domain.repository.IGameRepository;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private IGameRepository gameDAO;

    @Autowired
    private ICategoryRepository categoryDAO;

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

        Game g1 = new Game("League of Legends", "League of Legends es un juego de estrategia por equipos en el que dos equipos de cinco campeones se enfrentan para ver quién destruye antes la base del otro. Elige de entre un elenco de 140 campeones para realizar jugadas épicas, asesinar rivales y derribar torretas para alzarte con la victoria.","2009-10-27", "Riot Games");
        g1.setCategories(List.of(action, role, moba));
        Game g2 = new Game("Valorant", "Un juego de disparos táctico 5v5 basado en personajes.","2020-06-02", "Riot Games");
        g2.setCategories(List.of(action, threeD, tacticalShooter));
        Game g3 = new Game("Hollow Knight", "¡Forja tu propio camino en Hollow Knight! Una aventura épica a través de un vasto reino de insectos y héroes que se encuentra en ruinas. Explora cavernas tortuosas, combate contra criaturas corrompidas y entabla amistad con extraños insectos, todo en un estilo clásico en 2D dibujado a mano.","2017-02-24", "Team Cherry");
        g3.setCategories(List.of(action, adventure, twoD, metroidvania, platform));
        Game g4 = new Game("Kingdom Hearts", "Kingdom Hearts es el resultado de una colaboración entre Square y The Walt Disney Company.","2002-03-28", "Squaresoft");
        g4.setCategories(List.of(action, adventure, role, threeD));
        Game g5 = new Game("Kingdom Hearts Chain Of Memories", "Kingdom Hearts: Chain Of Memories retoma la historia donde acaba Kingdom Hearts 1 y los primeros 50-60 días de Kingdom Hearts 358/2 Days.","2004-11-11", "Square Enix");
        g5.setCategories(List.of(action, adventure, role, threeD));
        Game g6 = new Game("Kingdom Hearts 2", "Kingdom Hearts 2 es la secuela directa de Kingdom Hearts: Chain of Memories.","2005-12-22", "Square Enix");
        g6.setCategories(List.of(action, adventure, role, threeD));
        Game g7 = new Game("Counter-Strike: Global Offensive", "Un icónico juego de disparos táctico en primera persona donde equipos de terroristas y antiterroristas compiten en misiones estratégicas.", "2012-08-21", "Valve");
        g7.setCategories(List.of(action, tacticalShooter, threeD));
        Game g8 = new Game("Warface", "Un FPS táctico con modos cooperativos y competitivos, ofreciendo misiones dinámicas y múltiples clases de personajes.", "2013-10-21", "Crytek");
        g8.setCategories(List.of(action, tacticalShooter, threeD));
        Game g9 = new Game("Apex Legends", "Un shooter táctico basado en escuadrones con héroes únicos, ambientado en el universo de Titanfall.", "2019-02-04", "Respawn Entertainment");
        g9.setCategories(List.of(action, moba, threeD));
        Game g10 = new Game("Enlisted", "Un shooter táctico ambientado en la Segunda Guerra Mundial donde los jugadores controlan escuadrones de soldados en combates históricos.", "2021-04-08", "Darkflow Software");
        g10.setCategories(List.of(action, threeD));
        Game g11 = new Game("Super Mario Odyssey", "Mario emprende una aventura en un mundo de plataformas 3D para rescatar a la princesa Peach usando su nuevo compañero, Cappy.", "2017-10-27", "Nintendo");
        g11.setCategories(List.of(adventure, threeD, platform));
        Game g12 = new Game("Ori and the Blind Forest", "Un juego de plataformas que combina una hermosa narrativa con desafiantes mecánicas mientras Ori intenta salvar su bosque.", "2015-03-11", "Moon Studios");
        g12.setCategories(List.of(adventure, twoD, metroidvania));
        Game g13 = new Game("Celeste", "Ayuda a Madeline a escalar la montaña Celeste enfrentando desafiantes niveles de plataformas y sus propios miedos.", "2018-01-25", "Maddy Makes Games");
        g13.setCategories(List.of(adventure, twoD, metroidvania));
        Game g14 = new Game("The Witcher 3: Wild Hunt", "Un RPG de mundo abierto donde Geralt de Rivia busca a su hija adoptiva mientras enfrenta monstruos y decisiones morales.", "2015-05-19", "CD Projekt Red");
        g14.setCategories(List.of(action, role));
        Game g15 = new Game("Elden Ring", "Un juego de rol de acción ambientado en un mundo abierto, con una historia creada por Hidetaka Miyazaki y George R.R. Martin.", "2022-02-25", "FromSoftware");
        g15.setCategories(List.of(action, role, threeD));
        Game g16 = new Game("Final Fantasy XV", "Un juego de rol épico donde el príncipe Noctis y sus amigos intentan recuperar su reino de las fuerzas enemigas.", "2016-11-29", "Square Enix");
        g16.setCategories(List.of(action, role, threeD));

        gameDAO.saveAll(List.of(g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12, g13, g14, g15, g16));
    }
}