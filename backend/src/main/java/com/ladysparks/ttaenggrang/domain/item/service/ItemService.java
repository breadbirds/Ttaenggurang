package com.ladysparks.ttaenggrang.domain.item.service;

import com.ladysparks.ttaenggrang.domain.item.entity.Item;
import com.ladysparks.ttaenggrang.domain.item.dto.ItemDTO;
import com.ladysparks.ttaenggrang.domain.item.mapper.ItemMapper;
import com.ladysparks.ttaenggrang.domain.item.repository.ItemRepository;
import com.ladysparks.ttaenggrang.domain.student.service.StudentService;
import com.ladysparks.ttaenggrang.domain.teacher.service.TeacherService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final StudentService studentService;
    private final TeacherService teacherService;

    // 상품 등록
    public ItemDTO addItem(ItemDTO itemDto) {
        Long studentId = studentService.getCurrentStudentId();
        itemDto.setSellerId(studentId);
        Item item = itemMapper.toEntity(itemDto);
        Item savedItem = itemRepository.save(item);
        return itemMapper.toDto(savedItem);
    }

    // 상품 조회
    public List<ItemDTO> findItemList() {
        Long studentId = studentService.getCurrentStudentId();
        Long teacherId = studentService.findTeacherIdByStudentId(studentId);
        return itemRepository.findItemsByTeacherId(teacherId)
                .stream()
                .map(itemMapper::toDto)
                .collect(Collectors.toList());
    }

    // 특정 상품 상세 조회 - 존재하지 않으면 예외 발생
    public ItemDTO findItem(Long itemId) {
        return itemRepository.findById(itemId)
                .map(itemMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("해당 상품을 찾을 수 없습니다. ID: " + itemId));
    }

    // 특정 학생이 판매한 상품 조회
    public List<ItemDTO> findItemListBySeller() {
        Long studentId = studentService.getCurrentStudentId();
        return itemRepository.findBySellerId(studentId)
                .stream()
                .map(itemMapper::toDto)
                .collect(Collectors.toList());
    }

    // 특정 교사의 학근 배 상품 리스트 조회
    public List<ItemDTO> findItemListByTeacher() {
        Long teacherId = teacherService.getCurrentTeacherId();
        return itemRepository.findItemsByTeacherId(teacherId)
                .stream()
                .map(itemMapper::toDto)
                .collect(Collectors.toList());
    }

    // 특정 교사의 학급 내 판매 중인 상품 리스트 조회
    public List<ItemDTO> findActiveItemListByTeacher() {
        Long teacherId = teacherService.getCurrentTeacherId();
        return itemRepository.findActiveItemsByTeacherId(teacherId)
                .stream()
                .map(itemMapper::toDto)
                .collect(Collectors.toList());
    }

}
