package com.example.loudlygmz.seed;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.loudlygmz.DAO.ICategoryDAO;
import com.example.loudlygmz.DAO.IGameDAO;
import com.example.loudlygmz.entity.Category;
import com.example.loudlygmz.entity.Game;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private IGameDAO gameDAO;

    @Autowired
    private ICategoryDAO categoryDAO;

    @Override
    public void run(String... args) throws Exception {
        if (gameDAO.count() == 0 && categoryDAO.count() == 0) {  
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

            Game g1 = new Game("Counter-Strike: Global Offensive", "Un icónico juego de disparos táctico en primera persona donde equipos de terroristas y antiterroristas compiten en misiones estratégicas.", "2012-08-21", "Valve");
            g1.setCategories(List.of(action, tacticalShooter, threeD));
            Game g2 = new Game("Warface", "Un FPS táctico con modos cooperativos y competitivos, ofreciendo misiones dinámicas y múltiples clases de personajes.", "2013-10-21", "Crytek");
            g2.setCategories(List.of(action, tacticalShooter, threeD));
            Game g3 = new Game("Apex Legends", "Un shooter táctico basado en escuadrones con héroes únicos, ambientado en el universo de Titanfall.", "2019-02-04", "Respawn Entertainment");
            g3.setCategories(List.of(action, moba, threeD));
            Game g4 = new Game("Enlisted", "Un shooter táctico ambientado en la Segunda Guerra Mundial donde los jugadores controlan escuadrones de soldados en combates históricos.", "2021-04-08", "Darkflow Software");
            g4.setCategories(List.of(action, threeD));
            Game g5 = new Game("Super Mario Odyssey", "Mario emprende una aventura en un mundo de plataformas 3D para rescatar a la princesa Peach usando su nuevo compañero, Cappy.", "2017-10-27", "Nintendo");
            g5.setCategories(List.of(adventure, threeD, platform));
            Game g6 = new Game("Ori and the Blind Forest", "Un juego de plataformas que combina una hermosa narrativa con desafiantes mecánicas mientras Ori intenta salvar su bosque.", "2015-03-11", "Moon Studios");
            g6.setCategories(List.of(adventure, twoD, metroidvania));
            Game g7 = new Game("Celeste", "Ayuda a Madeline a escalar la montaña Celeste enfrentando desafiantes niveles de plataformas y sus propios miedos.", "2018-01-25", "Maddy Makes Games");
            g7.setCategories(List.of(adventure, twoD, metroidvania));
            Game g8 = new Game("The Witcher 3: Wild Hunt", "Un RPG de mundo abierto donde Geralt de Rivia busca a su hija adoptiva mientras enfrenta monstruos y decisiones morales.", "2015-05-19", "CD Projekt Red");
            g8.setCategories(List.of(action, role));
            Game g9 = new Game("Elden Ring", "Un juego de rol de acción ambientado en un mundo abierto, con una historia creada por Hidetaka Miyazaki y George R.R. Martin.", "2022-02-25", "FromSoftware");
            g9.setCategories(List.of(action, role, threeD));
            Game g10 = new Game("Final Fantasy XV", "Un juego de rol épico donde el príncipe Noctis y sus amigos intentan recuperar su reino de las fuerzas enemigas.", "2016-11-29", "Square Enix");
            g10.setCategories(List.of(action, role, threeD));

            gameDAO.saveAll(List.of(g1, g2,g3,g4,g5,g6,g7,g8,g9,g10));
        }
    }
    
    
}
