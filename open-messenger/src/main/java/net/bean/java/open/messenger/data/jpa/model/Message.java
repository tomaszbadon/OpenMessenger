package net.bean.java.open.messenger.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String contant;
    private boolean received;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date created_at;

    @ManyToOne(fetch = FetchType.EAGER)
    private User sender;

    @ManyToOne(fetch = FetchType.EAGER)
    private User recipient;

    @Column(name = "local_date_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime localDateTime;

}
