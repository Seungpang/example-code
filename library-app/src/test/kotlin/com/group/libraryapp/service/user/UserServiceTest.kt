package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest
    @Autowired
    constructor(
        private val userRepository: UserRepository,
        private val userService: UserService,
    ) {
        @AfterEach
        fun clean() {
            userRepository.deleteAll()
        }

        @Test
        fun saveUserTest() {
            val request = UserCreateRequest("김승래", 20)

            userService.saveUser(request)

            val results = userRepository.findAll()
            assertThat(results).hasSize(1)
            assertThat(results[0].name).isEqualTo(request.name)
            assertThat(results[0].age).isEqualTo(request.age)
        }

        @Test
        fun getUsersTest() {
            userRepository.saveAll(
                listOf(
                    User("A", 20),
                    User("B", 30),
                ),
            )

            val results = userService.getUsers()

            assertThat(results).hasSize(2)
            assertThat(results).extracting("name")
                .containsExactlyInAnyOrder("A", "B")
            assertThat(results).extracting("age")
                .containsExactlyInAnyOrder(20, 30)
        }

        @Test
        fun updateUserNameTest() {
            val savedUser = userRepository.save(User("A", 20))
            val request = UserUpdateRequest(savedUser.id, "B")

            userService.updateUserName(request)

            val result = userRepository.findAll()[0]

            assertThat(result.name).isEqualTo("B")
        }

        @Test
        fun deleteUserTest() {
            userRepository.save(User("A", 20))

            userService.deleteUser("A")

            assertThat(userRepository.findAll()).isEmpty()
        }
    }
