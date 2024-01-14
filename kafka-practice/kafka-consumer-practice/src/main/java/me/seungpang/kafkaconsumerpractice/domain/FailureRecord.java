package me.seungpang.kafkaconsumerpractice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class FailureRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String topic;
    private Integer keyValue;
    private String errorRecord;
    private Integer partition;
    private Long offsetsValue;
    private String exception;
    private String status;

    public void updateStatus(final String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "FailureRecord{" +
                "id=" + id +
                ", topic='" + topic + '\'' +
                ", keyValue=" + keyValue +
                ", errorRecord='" + errorRecord + '\'' +
                ", partition=" + partition +
                ", offsetsValue=" + offsetsValue +
                ", exception='" + exception + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
