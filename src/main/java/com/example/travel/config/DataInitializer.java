package com.example.travel.config;


// @Component
// @RequiredArgsConstructor

//TẠO TÀI KHOẢN MẶC ĐỊNH
public class DataInitializer { //implements CommandLineRunner

    // private final UserRepository userRepository;
    // private final PasswordEncoder passwordEncoder;

    // @Override
    // public void run(String... args) throws Exception {
        
    //     // 1. Tạo tài khoản Admin mặc định
    //     if (userRepository.findByUserName("admin1").isEmpty()) {
    //         UserEntity admin = new UserEntity();
    //         admin.setUserName("admin1");
    //         admin.setEmail("admin@gmail.com");
    //         // Mật khẩu sẽ được mã hóa BCrypt trước khi lưu
    //         admin.setPassWord(passwordEncoder.encode("123456")); 
    //         admin.setFullName("Nguyễn Long Nhât");
    //         admin.setPhoneNumber("0999888777");
    //         admin.setAddress("Quảng Trị");
    //         admin.setRole("Admin"); 
    //         admin.setStatus("Hoạt động");
    //         admin.setCreateAt(LocalDateTime.now());
    //         admin.setDoB(LocalDate.of(2000, 1, 1));
    //         admin.setAvatar("anhdaidien.jpg");

    //         userRepository.save(admin);
    //         //System.out.println(passwordEncoder.encode("12345"));

    //         System.out.println(">>> [DataInitializer] Đã tạo tài khoản ADMIN mặc định: admin / 123456");
            
    //     }

    //     // 2. Tạo tài khoản Khách hàng mẫu
    //     if (userRepository.findByUserName("user").isEmpty()) {
    //         UserEntity user = new UserEntity();
    //         user.setUserName("user");
    //         user.setEmail("user@gmail.com");
    //         user.setPassWord(passwordEncoder.encode("123456"));
    //         user.setFullName("Nguyễn Long Nhật");
    //         user.setPhoneNumber("0123456789");
    //         user.setRole("User");
    //         user.setStatus("Hoạt động");
    //         user.setAddress("Quảng Trị");
    //         user.setGender(1);
    //         user.setCreateAt(LocalDateTime.now());
    //         user.setDoB(LocalDate.of(2005, 11, 1));
    //         user.setAvatar("anhdaidien.jpg");

    //         userRepository.save(user);
    //         System.out.println(">>> [DataInitializer] Đã tạo tài khoản USER mặc định: user / 123456");
    //     }
    // }
}
