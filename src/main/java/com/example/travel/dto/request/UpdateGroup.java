package com.example.travel.dto.request;

/*
Tạo group để phân chia công việc update hoặc create ở request dto vì trong dto có dùng các @Notnull ,...
nhung chỉ áp dụng @Notnull,... cho chức năng tạo, còn chức năng cập nhật cho phép null để chi cập nhật những trường mà người dùng thay đổi

ví dụ dùng trong request dto
    @NotNull(message = "giá người lớn ko đc null", groups = CreateGroup.class)
    @Min(value = 0, message = "giá người lớn phải >= 0", groups = {CreateGroup.class, UpdateGroup.class})
 */
public interface UpdateGroup {

}
