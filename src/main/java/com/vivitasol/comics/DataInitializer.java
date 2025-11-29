package com.vivitasol.comics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private MangaRepository mangaRepository;

    @Override
    public void run(String... args) throws Exception {
        Manga manga1 = new Manga();
        manga1.setTitulo("Chainsaw Man");
        manga1.setAutor("Tatsuki Fujimoto");
        manga1.setAnioPublicacion(2018);
        manga1.setEditorial("Shueisha");
        manga1.setGenero("Shonen");
        manga1.setDescripcion("Denji es un joven que se fusiona con su perro demonio motosierra Pochita");
        manga1.setPrecio(10990.0);

        Manga manga2 = new Manga();
        manga2.setTitulo("Heaven Officials Blessing");
        manga2.setAutor("Mo Xiang Tong Xiu");
        manga2.setAnioPublicacion(2017);
        manga2.setEditorial("Seven Seas Entertainment");
        manga2.setGenero("Fantasia");
        manga2.setDescripcion("Xie Lian es un principe caido que asciende al cielo por tercera vez");
        manga2.setPrecio(25990.0);

        Manga manga3 = new Manga();
        manga3.setTitulo("JoJos Bizarre Adventure Phantom Blood");
        manga3.setAutor("Hirohiko Araki");
        manga3.setAnioPublicacion(1987);
        manga3.setEditorial("Shueisha");
        manga3.setGenero("Shonen");
        manga3.setDescripcion("La primera parte de la saga JoJo");
        manga3.setPrecio(15990.0);

        Manga manga4 = new Manga();
        manga4.setTitulo("Kaguya-sama Love is War");
        manga4.setAutor("Aka Akasaka");
        manga4.setAnioPublicacion(2015);
        manga4.setEditorial("Shueisha");
        manga4.setGenero("Comedia");
        manga4.setDescripcion("Dos genios luchan para hacer que el otro confiese su amor primero");
        manga4.setPrecio(10990.0);

        Manga manga5 = new Manga();
        manga5.setTitulo("Made in Abyss");
        manga5.setAutor("Akihito Tsukushi");
        manga5.setAnioPublicacion(2012);
        manga5.setEditorial("Takeshobo");
        manga5.setGenero("Aventura");
        manga5.setDescripcion("Riko y Reg descienden al misterioso Abismo");
        manga5.setPrecio(12990.0);

        Manga manga6 = new Manga();
        manga6.setTitulo("Tokyo Revengers");
        manga6.setAutor("Ken Wakui");
        manga6.setAnioPublicacion(2017);
        manga6.setEditorial("Kodansha");
        manga6.setGenero("Shonen");
        manga6.setDescripcion("Takemichi viaja en el tiempo para salvar a su ex novia");
        manga6.setPrecio(10990.0);

        Manga manga7 = new Manga();
        manga7.setTitulo("Yona of the Dawn");
        manga7.setAutor("Mizuho Kusanagi");
        manga7.setAnioPublicacion(2009);
        manga7.setEditorial("Hakusensha");
        manga7.setGenero("Fantasia");
        manga7.setDescripcion("La princesa Yona busca a los Cuatro Dragones");
        manga7.setPrecio(10990.0);

        mangaRepository.save(manga1);
        mangaRepository.save(manga2);
        mangaRepository.save(manga3);
        mangaRepository.save(manga4);
        mangaRepository.save(manga5);
        mangaRepository.save(manga6);
        mangaRepository.save(manga7);

        System.out.println("Base de datos inicializada con " + mangaRepository.count() + " mangas");
    }
}