package com.server.starter.file.controller;

import com.server.starter.file.dto.FileRecordDTO;
import com.server.starter.file.service.FileRecordService;
import com.server.starter.file.vo.FileRecordVO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * file controller.
 *
 * @author wq li
 */
@RestController
@RequestMapping("/files")
public class FileController {

    private final Logger logger = LoggerFactory.getLogger(FileController.class);

    private final FileRecordService fileRecordService;

    /**
     * <p>Constructor for RegionController.</p>
     * <p>
     * //     * @param regionService a {@link FileRecordService} object
     */
    public FileController(FileRecordService fileRecordService) {
        this.fileRecordService = fileRecordService;
    }

    /**
     * 分页查询
     *
     * @param page       页码
     * @param size       大小
     * @param sortBy     排序字段
     * @param descending 排序方向
     * @return 查询的数据集，异常时返回204状态码
     */
    @PreAuthorize("hasAuthority('SCOPE_files:read')")
    @GetMapping
    public ResponseEntity<Page<FileRecordVO>> retrieve(@RequestParam int page, @RequestParam int size,
                                                       String sortBy, boolean descending, String name) {
        Page<FileRecordVO> voPage;
        try {
            voPage = fileRecordService.retrieve(page, size, sortBy, descending, name);
        } catch (Exception e) {
            logger.error("Retrieve region occurred an error: ", e);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(voPage);
    }

    /**
     * 根据 id 查询
     *
     * @param id 业务id
     * @return 查询的数据，异常时返回204状态码
     */
    @PreAuthorize("hasAuthority('SCOPE_files:read')")
    @GetMapping("/{id}")
    public ResponseEntity<FileRecordVO> fetch(@PathVariable Long id) {
        FileRecordVO vo;
        try {
            vo = fileRecordService.fetch(id);
        } catch (Exception e) {
            logger.error("Fetch region occurred an error: ", e);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(vo);
    }

    /**
     * 是否存在
     *
     * @param name 名称
     * @return 如果查询到数据，返回查询到的信息，否则返回204状态码
     */
    @PreAuthorize("hasAuthority('SCOPE_files:read')")
    @GetMapping("/{name}/exist")
    public ResponseEntity<Boolean> exist(@PathVariable String name) {
        boolean exist;
        try {
            exist = fileRecordService.exist(name);
        } catch (Exception e) {
            logger.info("Query region exist occurred an error: ", e);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(exist);
    }

    /**
     * 添加信息
     *
     * @param dto 要添加的数据
     * @return 如果添加数据成功，返回添加后的信息，否则返回417状态码
     */
    @PreAuthorize("hasAuthority('SCOPE_files:write')")
    @PostMapping
    public ResponseEntity<FileRecordVO> create(@RequestBody @Valid FileRecordDTO dto) {
        FileRecordVO vo;
        try {
            vo = fileRecordService.create(dto);
        } catch (Exception e) {
            logger.error("Create region occurred an error: ", e);
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(vo);
    }

    /**
     * 删除信息
     *
     * @param id 主键
     * @return 如果删除成功，返回200状态码，否则返回417状态码
     */
    @PreAuthorize("hasAuthority('SCOPE_files:write')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        try {
            fileRecordService.remove(id);
        } catch (Exception e) {
            logger.error("Remove region occurred an error: ", e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
        return ResponseEntity.ok().build();
    }
}
