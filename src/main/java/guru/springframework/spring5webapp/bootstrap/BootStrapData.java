package guru.springframework.spring5webapp.bootstrap;

import guru.springframework.spring5webapp.domain.Author;
import guru.springframework.spring5webapp.domain.Book;
import guru.springframework.spring5webapp.domain.Publisher;
import guru.springframework.spring5webapp.repository.AuthorRepository;
import guru.springframework.spring5webapp.repository.BookRepository;
import guru.springframework.spring5webapp.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BootStrapData implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    @Override
    public void run(String... args) throws Exception {
        Publisher mif = new Publisher("Mann, Ivanov, Ferber",
                "Osennyaya st.", "Moscow",
                "Moscow", "Russia", "115225");

        publisherRepository.save(mif);

        Author eric = new Author("Eric", "Evans");
        Book ddd = new Book("Domain Driven Design", "123123");
        eric.getBooks().add(ddd);
        ddd.getAuthors().add(eric);
        ddd.setPublisher(mif);
        mif.getBooks().add(ddd);

        authorRepository.save(eric);
        bookRepository.save(ddd);
        publisherRepository.save(mif);

        Author rod = new Author("Rod", "Johnson");
        Book noEJB = new Book("J2EE Development without EJB", "234252345");
        rod.getBooks().add(noEJB);
        noEJB.getAuthors().add(rod);
        noEJB.setPublisher(mif);
        mif.getBooks().add(noEJB);

        authorRepository.save(rod);
        bookRepository.save(noEJB);
        publisherRepository.save(mif);

        log.info("Started in Bootstrap");
        log.info("Number of Books: {}", bookRepository.count());
        for (Book book : bookRepository.findAll()) {
            log.info(book.toString());
        }
        log.info("Number of Authors: {}", authorRepository.count());
        for (Author author : authorRepository.findAll()) {
            log.info(author.toString());
        }
        log.info("Number of Publishers: {}", publisherRepository.count());
        log.info("'{}' Number of Books: {}", mif.getName(), mif.getBooks().size());
    }
}
