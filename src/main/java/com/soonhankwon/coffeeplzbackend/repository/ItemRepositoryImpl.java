package com.soonhankwon.coffeeplzbackend.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.soonhankwon.coffeeplzbackend.entity.QOrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class ItemRepositoryImpl implements CustomItemRepository {
    private final JPAQueryFactory queryFactory;
    QOrderItem orderItem = QOrderItem.orderItem;

    @Override
    public List<Long> favoriteItems() {
        LocalDate weekBefore = LocalDate.now().minusDays(7);
        LocalDate yesterday = LocalDate.now();

        return queryFactory.select(orderItem.item.id)
                .from(orderItem)
                .where(orderItem.createdAt.between(weekBefore.atStartOfDay(), yesterday.atStartOfDay()))
                .groupBy(orderItem.item.id)
                .orderBy(orderItem.quantity.sum().desc())
                .limit(3)
                .fetch();
    }
}
