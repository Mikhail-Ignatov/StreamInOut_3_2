import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        GameProgress gameProgress1 = new GameProgress(94, 10, 2, 254.32);
        GameProgress gameProgress2 = new GameProgress(50, 12, 6, 415);
        GameProgress gameProgress3 = new GameProgress(70, 7, 15, 555);

        saveGame("C://Games/savegames/save1.dat", gameProgress1);
        saveGame("C://Games/savegames/save2.dat", gameProgress2);
        saveGame("C://Games/savegames/save3.dat", gameProgress3);

        List<String> filesPath = new ArrayList<>();
        filesPath.add("C://Games/savegames/save1.dat");
        filesPath.add("C://Games/savegames/save2.dat");
        filesPath.add("C://Games/savegames/save3.dat");
        zipFiles("C://Games/savegames/save.zip", filesPath);

        File save1 = new File("C://Games/savegames/save1.dat");
        if (save1.delete())
            System.out.println("Файл " + save1 + " удален");
        File save2 = new File("C://Games/savegames/save2.dat");
        if (save2.delete())
            System.out.println("Файл " + save2 + " удален");
        File save3 = new File("C://Games/savegames/save3.dat");
        if (save3.delete())
            System.out.println("Файл " + save3 + " удален");

    }

    public static void saveGame(String filePath, GameProgress gameProgress) {
        // откроем выходной поток для записи в файл
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            // запишем экземпляр класса в файл
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void zipFiles(String zipPath, List<String> filesPath) throws FileNotFoundException {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipPath))) {
            for (int i = 0; i < filesPath.size(); i++) {
                try (FileInputStream fis = new FileInputStream(filesPath.get(i))) {
                    ZipEntry entry = new ZipEntry("save" + (i + 1) + ".dat");
                    zout.putNextEntry(entry);
                    // считываем содержимое файла в массив byte
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    // добавляем содержимое к архиву
                    zout.write(buffer);
                    // закрываем текущую запись для новой записи
                    zout.closeEntry();
                } catch (Exception ex) {
                    throw new FileNotFoundException();
                }
            }
        } catch (Exception ex) {
            throw new FileNotFoundException();
        }
    }
}