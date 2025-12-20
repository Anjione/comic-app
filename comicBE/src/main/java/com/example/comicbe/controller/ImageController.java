package com.example.comicbe.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

@RestController
@RequestMapping("/api/images")
public class ImageController {

//    @PostMapping("/upload")
//    public String uploadImage(@RequestParam("file") MultipartFile file) {
//        try {
//            // Đọc ảnh gốc
//            BufferedImage originalImage = ImageIO.read(file.getInputStream());
//
//            // Tên file đầu ra
//            String outputFileName = "compressed_" + System.currentTimeMillis() + ".webp";
//            File outputFile = new File("uploads/" + outputFileName);
//            outputFile.getParentFile().mkdirs();
//
//            // Tìm writer WebP
//            Iterator<ImageWriter> writers = ImageIO.getImageWritersByMIMEType("image/webp");
//            if (!writers.hasNext()) {
//                throw new IllegalStateException("No WebP writer found");
//            }
//            ImageWriter writer = writers.next();
//
//            // Tạo output stream
//            try (ImageOutputStream ios = ImageIO.createImageOutputStream(outputFile)) {
//                writer.setOutput(ios);
//
//                // Cấu hình nén (0.0f = kém, 1.0f = tốt nhất)
//                var param = writer.getDefaultWriteParam();
//                param.setCompressionMode(javax.imageio.ImageWriteParam.MODE_EXPLICIT);
//                param.setCompressionQuality(0.2f); // 75% chất lượng
//
//                writer.write(null, new javax.imageio.IIOImage(originalImage, null, null), param);
//                writer.dispose();
//            }
//
//            return "Đã lưu ảnh nén tại: " + outputFile.getAbsolutePath();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "Lỗi khi xử lý ảnh: " + e.getMessage();
//        }
//    }


    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            BufferedImage original = ImageIO.read(file.getInputStream());
            File outputFile = new File("uploads/compressed_" + System.currentTimeMillis() + ".webp");
            outputFile.getParentFile().mkdirs();

            Iterator<ImageWriter> writers = ImageIO.getImageWritersByMIMEType("image/webp");
            if (!writers.hasNext()) {
                throw new IllegalStateException("No WebP writer found");
            }
            ImageWriter writer = writers.next();
            ImageWriteParam param = writer.getDefaultWriteParam();

            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);

            String[] types = param.getCompressionTypes();
            if (types != null && types.length > 0) {
                param.setCompressionType(types[0]); // chọn loại đầu tiên (Lossy)
            } else {
                throw new IllegalStateException("No supported compression types for WebP");
            }

            param.setCompressionQuality(0.7f);

            try (ImageOutputStream ios = ImageIO.createImageOutputStream(outputFile)) {
                writer.setOutput(ios);
                writer.write(null, new IIOImage(original, null, null), param);
                writer.dispose();
            }



            return "Đã lưu ảnh nén và chuyển sang WebP tại: " + outputFile.getAbsolutePath();

        } catch (IOException e) {
            e.printStackTrace();
            return "Lỗi khi xử lý ảnh: " + e.getMessage();
        }
    }

    // Helper class lưu tên file + ảnh
    private static class BufferedImageEntry {
        private final String fileName;
        private final BufferedImage image;

        public BufferedImageEntry(String fileName, BufferedImage image) {
            this.fileName = fileName;
            this.image = image;
        }

        public String getFileName() { return fileName; }
        public BufferedImage getImage() { return image; }
    }

    @PostMapping("/upload-zip")
    public String uploadZip(@RequestParam("file") MultipartFile zipFile) {
        StringBuilder result = new StringBuilder();
        File outputDir = new File("uploads/");
        outputDir.mkdirs();

        try (ZipInputStream zis = new ZipInputStream(zipFile.getInputStream())) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                String name = entry.getName();

                // Bỏ qua thư mục trong ZIP
                if (entry.isDirectory()) {
                    zis.closeEntry();
                    continue;
                }

                // Chỉ xử lý file ảnh (png/jpg/jpeg)
                if (!name.toLowerCase().matches(".*\\.(png|jpg|jpeg)$")) {
                    zis.closeEntry();
                    continue;
                }

                // Đọc ảnh từ ZIP
                BufferedImage original = ImageIO.read(zis);
                if (original == null) {
                    zis.closeEntry();
                    continue;
                }

                // Tạo file WebP đầu ra
                String outputFileName = name.substring(0, name.lastIndexOf('.'))
                        + "_" + System.currentTimeMillis() + ".webp";
                File outputFile = new File(outputDir, outputFileName);

                // Lấy writer WebP
                Iterator<ImageWriter> writers = ImageIO.getImageWritersByMIMEType("image/webp");
                if (!writers.hasNext()) {
                    throw new IllegalStateException("No WebP writer found");
                }
                ImageWriter writer = writers.next();

                ImageWriteParam param = writer.getDefaultWriteParam();
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);

                // Lấy compression type hợp lệ
                String[] types = param.getCompressionTypes();
                if (types != null && types.length > 0) {
                    param.setCompressionType(types[0]); // thường là "Lossy"
                } else {
                    throw new IllegalStateException("No supported compression types for WebP");
                }

                param.setCompressionQuality(0.2f);

                try (ImageOutputStream ios = ImageIO.createImageOutputStream(outputFile)) {
                    writer.setOutput(ios);
                    writer.write(null, new IIOImage(original, null, null), param);
                    writer.dispose();
                }

                result.append("Đã xử lý: ").append(name)
                        .append(" -> ").append(outputFile.getAbsolutePath()).append("\n");

                zis.closeEntry();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return "Lỗi khi xử lý ZIP: " + e.getMessage();
        }

        return result.toString();
    }

//    @PostMapping("/upload-zip-mt")
//    public String uploadZipMultithread(@RequestParam("file") MultipartFile zipFile) {
//        StringBuilder result = new StringBuilder();
//        File outputDir = new File("uploads/");
//        outputDir.mkdirs();
//
//        System.out.println("Kích thước file ZIP server nhận: " + zipFile.getSize());
//
//
//        List<BufferedImageEntry> imagesToProcess = new ArrayList<>();
//
//        // ⭐ Bước 0: Lưu MultipartFile thành file tạm
//        File tempZip = new File(outputDir,"tmp_" + System.currentTimeMillis() + ".zip");
//        try (FileOutputStream fos = new FileOutputStream(tempZip)) {
//            fos.write(zipFile.getBytes());
//        } catch (Exception e) {
//            return "Không thể lưu file ZIP tạm: " + e.getMessage();
//        }
//
//        // ⭐ Bước 1: Đọc ZIP bằng ZipFile (đảm bảo đọc đủ 100%)
//        try (ZipFile zip = new ZipFile(tempZip)) {
//
//            Enumeration<? extends ZipEntry> entries = zip.entries();
//
//            while (entries.hasMoreElements()) {
//                ZipEntry entry = entries.nextElement();
//
//                String name = entry.getName().toLowerCase();
//
//                if (entry.isDirectory() || !name.matches(".*\\.(png|jpg|jpeg|webp)$")) {
//                    continue;
//                }
//
//                try (InputStream is = zip.getInputStream(entry)) {
//                    // Đọc toàn bộ entry vào RAM
//                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                    byte[] buffer = new byte[8192];
//                    int len;
//
//                    while ((len = is.read(buffer)) != -1) {
//                        baos.write(buffer, 0, len);
//                    }
//
//                    byte[] data = baos.toByteArray();
//
//                    BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
//
//                    if (img == null) {
//                        System.out.println("Không thể đọc ảnh: " + entry.getName());
//                        continue;
//                    }
//
//                    imagesToProcess.add(new BufferedImageEntry(entry.getName(), img));
//                }
//            }
//
//        } catch (Exception e) {
//            return "Lỗi khi đọc ZIP: " + e.getMessage();
//        }
//
//        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//
//        List<Future<String>> futures = new ArrayList<>();
//
//        for (BufferedImageEntry entry : imagesToProcess) {
//            futures.add(executor.submit(() -> {
//                String name = entry.getFileName();
//                BufferedImage original = entry.getImage();
//
//                String outputFileName =
//                        name.substring(0, name.lastIndexOf('.'))
//                                + "_" + System.currentTimeMillis() + UUID.randomUUID() + ".webp";
//
//                File outputFile = new File(outputDir, outputFileName);
//
//                Iterator<ImageWriter> writers = ImageIO.getImageWritersByMIMEType("image/webp");
//                if (!writers.hasNext()) {
//                    throw new IllegalStateException("No WebP writer found");
//                }
//
//                ImageWriter writer = writers.next();
//                ImageWriteParam param = writer.getDefaultWriteParam();
//                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//
//                String[] types = param.getCompressionTypes();
//                if (types != null && types.length > 0) {
//                    param.setCompressionType(types[0]);
//                } else {
//                    throw new IllegalStateException("No supported compression types for WebP");
//                }
//
//                param.setCompressionQuality(0.2f);
//
//                try (ImageOutputStream ios = ImageIO.createImageOutputStream(outputFile)) {
//                    writer.setOutput(ios);
//                    writer.write(null, new IIOImage(original, null, null), param);
//                    writer.dispose();
//                }
//
//                return "Đã xử lý: " + name + " -> " + outputFile.getAbsolutePath();
//            }));
//        }
//
//        // ⭐ Bước 3: Lấy kết quả
//        for (Future<String> f : futures) {
//            try {
//                result.append(f.get()).append("\n");
//            } catch (Exception e) {
//                result.append("Lỗi khi xử lý ảnh: ").append(e.getMessage()).append("\n");
//            }
//        }
//
//        executor.shutdown();
//        tempZip.delete(); // xóa file tạm
//
//        return result.toString();
//    }

//    @PostMapping("/upload-zip-mt")
//    public String uploadZipMultithread(@RequestParam("file") MultipartFile zipFile) {
//        StringBuilder result = new StringBuilder();
//        File outputDir = new File("uploads/");
//        outputDir.mkdirs();
//
//        List<BufferedImageEntry> imagesToProcess = new ArrayList<>();
//
//        // Bước 1: Đọc ZIP, lấy tất cả file ảnh vào danh sách
//        try (ZipInputStream zis = new ZipInputStream(zipFile.getInputStream())) {
//            ZipEntry entry;
//            while ((entry = zis.getNextEntry()) != null) {
//                String name = entry.getName();
//
//                if (entry.isDirectory() || !name.toLowerCase().matches(".*\\.(png|jpg|jpeg|webp)$")) {
//                    zis.closeEntry();
//                    continue;
//                }
//
////                BufferedImage image = ImageIO.read(zis);
//
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                byte[] buffer = new byte[8192];
//                int len;
//
//                while ((len = zis.read(buffer)) > 0) {
//                    baos.write(buffer, 0, len);
//                }
//                byte[] data = baos.toByteArray();
//
//                BufferedImage image = ImageIO.read(new ByteArrayInputStream(data));
//
//                if (image != null) {
//                    imagesToProcess.add(new BufferedImageEntry(name, image));
//                }
//
//                zis.closeEntry();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "Lỗi khi đọc ZIP: " + e.getMessage();
//        }
//
//        // Bước 2: Tạo ExecutorService (Thread Pool)
//        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
////        ExecutorService executor = Executors.newFixedThreadPool(1);
//
//        List<Future<String>> futures = new ArrayList<>();
//        for (BufferedImageEntry entry : imagesToProcess) {
//            futures.add(executor.submit(() -> {
//                String name = entry.getFileName();
//                BufferedImage original = entry.getImage();
//
//                String outputFileName = name.substring(0, name.lastIndexOf('.'))
//                        + "_" + System.currentTimeMillis() + UUID.randomUUID() + ".webp";
//                File outputFile = new File(outputDir, outputFileName);
//
//                Iterator<ImageWriter> writers = ImageIO.getImageWritersByMIMEType("image/webp");
//                if (!writers.hasNext()) {
//                    throw new IllegalStateException("No WebP writer found");
//                }
//                ImageWriter writer = writers.next();
//
//                ImageWriteParam param = writer.getDefaultWriteParam();
//                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//
//                String[] types = param.getCompressionTypes();
//                if (types != null && types.length > 0) {
//                    param.setCompressionType(types[0]);
//                } else {
//                    throw new IllegalStateException("No supported compression types for WebP");
//                }
//
//                param.setCompressionQuality(0.2f);
//
//                try (ImageOutputStream ios = ImageIO.createImageOutputStream(outputFile)) {
//                    writer.setOutput(ios);
//                    writer.write(null, new IIOImage(original, null, null), param);
//                    writer.dispose();
//                }
//
//                return "Đã xử lý: " + name + " -> " + outputFile.getAbsolutePath();
//            }));
//        }
//
//        // Bước 3: Lấy kết quả
//        for (Future<String> f : futures) {
//            try {
//                result.append(f.get()).append("\n");
//            } catch (Exception e) {
//                e.printStackTrace();
//                result.append("Lỗi khi xử lý một ảnh: ").append(e.getMessage()).append("\n");
//            }
//        }
//
//        executor.shutdown();
//        return result.toString();
//    }


@PostMapping("/upload-zip-mt")
public String uploadZipMultithread(@RequestParam("file") MultipartFile zipFile) {
    StringBuilder result = new StringBuilder();
    File outputDir = new File("uploads/");
    outputDir.mkdirs();

    // Tạo ThreadPool
    int numThreads = Runtime.getRuntime().availableProcessors();
    ExecutorService executor = Executors.newFixedThreadPool(numThreads);

    try (ZipInputStream zis = new ZipInputStream(zipFile.getInputStream())) {
        ZipEntry entry;
        List<Future<String>> futures = new ArrayList<>();

        while ((entry = zis.getNextEntry()) != null) {
            String name = entry.getName();

            // Bỏ qua thư mục hoặc file không phải ảnh
            if (entry.isDirectory() || !name.toLowerCase().matches(".*\\.(png|jpg|jpeg|webp)$")) {
                zis.closeEntry();
                continue;
            }

            // Đọc toàn bộ dữ liệu của entry vào byte[]
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[8192];
            int len;
            while ((len = zis.read(buffer)) > 0) {
                baos.write(buffer, 0, len);
            }
            byte[] data = baos.toByteArray();
            zis.closeEntry();

            // Submit task nén ảnh sang WebP
            futures.add(executor.submit(() -> {
                try {
                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(data));
                    if (image == null) return "File không đọc được: " + name;

                    // Tạo file output
                    String outputFileName = name.substring(0, name.lastIndexOf('.'))
                            + "_" + System.currentTimeMillis() + "_" + UUID.randomUUID() + ".webp";
                    File outputFile = new File(outputDir, outputFileName);

                    // Lấy WebP writer
                    Iterator<ImageWriter> writers = ImageIO.getImageWritersByMIMEType("image/webp");
                    if (!writers.hasNext()) return "Không tìm thấy WebP writer";

                    ImageWriter writer = writers.next();
                    ImageWriteParam param = writer.getDefaultWriteParam();
                    param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);

                    String[] types = param.getCompressionTypes();
                    if (types != null && types.length > 0) {
                        param.setCompressionType(types[0]); // Lossy
                    }

                    param.setCompressionQuality(0.2f); // chất lượng nén

                    try (ImageOutputStream ios = ImageIO.createImageOutputStream(outputFile)) {
                        writer.setOutput(ios);
                        writer.write(null, new IIOImage(image, null, null), param);
                        writer.dispose();
                    }

                    image.flush(); // giải phóng bộ nhớ
                    return "Đã xử lý: " + name + " -> " + outputFile.getAbsolutePath();

                } catch (Exception e) {
                    e.printStackTrace();
                    return "Lỗi khi xử lý: " + name + " -> " + e.getMessage();
                }
            }));
        }

        // Lấy kết quả tất cả futures
        for (Future<String> f : futures) {
            try {
                result.append(f.get()).append("\n");
            } catch (Exception e) {
                e.printStackTrace();
                result.append("Lỗi khi lấy kết quả một ảnh: ").append(e.getMessage()).append("\n");
            }
        }

    } catch (IOException e) {
        e.printStackTrace();
        return "Lỗi khi đọc ZIP: " + e.getMessage();
    } finally {
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    return result.toString();
}
}

