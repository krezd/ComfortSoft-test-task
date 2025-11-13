package ru.krezd.comfortsofttask.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService
{
    private List<Integer> numbers = new ArrayList<Integer>();
    private String currentFile;

    public int findNumber(String pathToFile, int N) throws Exception
    {
        if (numbers.isEmpty() || !currentFile.equals(pathToFile))
        {
            numbers = readNumbersFromExcel(pathToFile);
            quickSort(numbers, 0, numbers.size() -1);
        }

        if (numbers.size() < N)
        {
            throw new IllegalArgumentException("Чисел в файле меньше чем число N = " + N);
        }

        return numbers.get(N - 1);
    }

    private List<Integer> readNumbersFromExcel(String filePath) throws Exception {
        List<Integer> tempNumbers = new ArrayList<>();

        File file = new File(filePath);
        if (!file.exists())
        {
            throw new IllegalArgumentException("Файла не существует: " + filePath);
        }

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis))
        {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                Cell cell = row.getCell(0);
                if (cell != null && cell.getCellType() == CellType.NUMERIC)
                {
                    double value = cell.getNumericCellValue();
                    if (value == Math.floor(value))
                    {
                        numbers.add((int) value);
                    }
                }
            }
        }
        currentFile = filePath;
        return numbers;
    }

    private void quickSort(List<Integer> list, int low, int high) {
        if (low < high) {
            int pivot = partition(list, low, high);

            quickSort(list, low, pivot - 1);
            quickSort(list, pivot + 1, high);
        }
    }

    private int partition(List<Integer> list, int low, int high) {
        int pivot = list.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (list.get(j) <= pivot) {
                i++;
                swap(list, i, j);
            }
        }

        swap(list, i + 1, high);
        return i + 1;
    }

    private void swap(List<Integer> list, int i, int j) {
        int temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}
