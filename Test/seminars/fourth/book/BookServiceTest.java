package seminars.fourth.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    private BookService bookService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        bookService = new BookService(bookRepository);
        setUpMockData();
    }

    private void setUpMockData() {
        List<Book> expectedBooks = Arrays.asList(
                new Book("1", "Book1", "Author1"),
                new Book("2", "Book2", "Author2")
        );
        when(bookRepository.findAll()).thenReturn(expectedBooks);

        for (Book book : expectedBooks) {
            when(bookRepository.findById(book.getId())).thenReturn(book);
        }
    }

    @Test
    public void testFindBookById() {
        String bookId = "1";
        Book expectedBook = new Book("1", "Book1", "Author1");

        Book actualBook = bookService.findBookById(bookId);

        assertEquals(expectedBook, actualBook);
    }

    @Test
    public void testFindAllBooks() {
        List<Book> actualBooks = bookService.findAllBooks();

        assertEquals(2, actualBooks.size());
        assertEquals("Book1", actualBooks.get(0).getTitle());
        assertEquals("Book2", actualBooks.get(1).getTitle());
    }
}