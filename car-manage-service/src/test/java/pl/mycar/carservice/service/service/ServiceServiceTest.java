package pl.mycar.carservice.service.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import pl.mycar.carservice.exception.InvalidFilesException;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ServiceServiceTest {
  private ServiceService serviceService;

  @Before
  public void init() {
    serviceService = new ServiceServiceImpl(null, null);
  }

  @Test(expected = InvalidFilesException.class)
  public void uploading6FilesShouldThrowInvalidFilesException() {
    // arrange
    List<MultipartFile> files = new ArrayList<MultipartFile>() {{
      add(new MockMultipartFile("1.png", "1.png", MediaType.IMAGE_PNG_VALUE, new byte[]{}));
      add(new MockMultipartFile("2.png", "2.png", MediaType.IMAGE_PNG_VALUE, new byte[]{}));
      add(new MockMultipartFile("3.png", "3.png", MediaType.IMAGE_PNG_VALUE, new byte[]{}));
      add(new MockMultipartFile("4.png", "4.png", MediaType.IMAGE_PNG_VALUE, new byte[]{}));
      add(new MockMultipartFile("5.png", "5.png", MediaType.IMAGE_PNG_VALUE, new byte[]{}));
      add(new MockMultipartFile("6.png", "6.png", MediaType.IMAGE_PNG_VALUE, new byte[]{}));
    }};

    // act
    serviceService.validateFiles(files);

    // assert exception is thrown
  }

  @Test(expected = InvalidFilesException.class)
  public void uploadingPdfShouldThrowInvalidFilesException() {
    // arrange
    List<MultipartFile> files = new ArrayList<MultipartFile>() {{
      add(new MockMultipartFile("1.png", "1.png", MediaType.IMAGE_PNG_VALUE, new byte[]{}));
      add(new MockMultipartFile("2.pdf", "2.pdf", MediaType.APPLICATION_PDF_VALUE, new byte[]{}));
    }};

    // act
    serviceService.validateFiles(files);

    // assert exception is thrown
  }
}