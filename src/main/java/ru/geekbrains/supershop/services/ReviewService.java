package ru.geekbrains.supershop.services;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.geekbrains.supershop.exceptions.EntityNotFoundException;
import ru.geekbrains.supershop.persistence.entities.Product;
import ru.geekbrains.supershop.persistence.entities.Review;
import ru.geekbrains.supershop.persistence.entities.Shopuser;
import ru.geekbrains.supershop.persistence.repositories.ReviewRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Optional<List<Review>> getReviewsByProduct(Product product) {
        return reviewRepository.findByProduct(product);
    }

    public Optional<List<Review>> getReviewsByShopuser(Shopuser shopuser) {
        return reviewRepository.findByShopuser(shopuser);
    }

    public void save(Review review) {
        reviewRepository.save(review);
    }

    public UUID moderate(UUID id, String option) throws EntityNotFoundException {
        Review review = reviewRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Oops! Review " + id + " wasn't found!")
        );
        review.setApproved(option.equals("approve"));
        save(review);
        return review.getProduct().getId();
    }

    public List<Review> getAll() {
        return reviewRepository.findAll();
    }

    @Transactional
    public void removeReviewById(UUID id) {
        reviewRepository.removeReviewById(id);
    }
}