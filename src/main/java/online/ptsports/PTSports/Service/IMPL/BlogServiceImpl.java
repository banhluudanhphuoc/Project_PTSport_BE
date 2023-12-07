//package online.ptsports.PTSports.Service.IMPL;
//
//
//
//import online.ptsports.PTSports.DTO.BlogDto;
//import online.ptsports.PTSports.Entity.Blog;
//import online.ptsports.PTSports.Entity.Category;
//import online.ptsports.PTSports.Exeption.ResoureNotFoundException;
//import online.ptsports.PTSports.Repository.BlogRepo;
//import online.ptsports.PTSports.Repository.CategoryRepo;
//import online.ptsports.PTSports.Service.BlogService;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class BlogServiceImpl implements BlogService {
//    @Autowired
//    BlogRepo blogRepo;
//
//    @Autowired
//    ModelMapper modelMapper;
//
//    @Autowired
//    CategoryRepo categoryRepo;
//
//    @Override
//    public BlogDto createBlog(BlogDto blogDto) {
//        Blog blog = convertToBlog(blogDto);
//        Category category = categoryRepo.findById(blogDto.getCategoryID()).orElseThrow(()
//                -> new ResoureNotFoundException("Category", "ID", blogDto.getCategoryID()));
//        blog.setCategory(category);
//        blogRepo.save(blog);
//        return convertToBlogDto(blog);
//    }
//
//    @Override
//    public BlogDto updateBlog(BlogDto blogDto, Integer BlogId) {
//        Blog blog = blogRepo.findById(BlogId).orElseThrow(() -> new ResoureNotFoundException("Blog", "ID", BlogId));
//
//        Blog blog1 = convertToBlog(blogDto);
//        blog1.setBlogId(blog.getBlogId());
//
//        Category category = categoryRepo.findById(blogDto.getCategoryID()).orElseThrow(() -> new ResoureNotFoundException("Category", "ID", blogDto.getCategoryID()));
//        blog1.setCategory(category);
//        blogRepo.save(blog1);
//        return convertToBlogDto(blog1);
//    }
//
//    @Override
//    public BlogDto getBlogById(Integer blogId) {
//        Blog blog = blogRepo.findById(blogId).orElseThrow(() -> new ResoureNotFoundException("Blog", "ID", blogId));
//
//        return convertToBlogDto(blog);
//    }
//
//    @Override
//    public List<BlogDto> getAllBlogs() {
//        List<Blog>blogs = blogRepo.findAll();
//        List<BlogDto> blogDtos = new ArrayList<>();
//        for (int i = 0; i < blogs.size(); i++) {
//            blogDtos.add(convertToBlogDto(blogs.get(i)));
//        }
//        return blogDtos;
//    }
//
//    @Override
//    public void deleteBlog(Integer blogId) {
//        Blog blog = blogRepo.findById(blogId).orElseThrow(() -> new ResoureNotFoundException("Blog", "ID", blogId));
//blogRepo.deleteById(blog.getBlogId());
//    }
//
//
//    @Override
//    public BlogDto convertToBlogDto(Blog blog) {
//        return modelMapper.map(blog, BlogDto.class);
//    }
//
//    @Override
//    public Blog convertToBlog(BlogDto blogDto) {
//        return modelMapper.map(blogDto, Blog.class);
//    }
//}

package online.ptsports.PTSports.Service.IMPL;


import java.util.stream.Collectors;

import online.ptsports.PTSports.DTO.BlogDto;
import online.ptsports.PTSports.Entity.Blog;

import online.ptsports.PTSports.Exeption.ResoureNotFoundException;
import online.ptsports.PTSports.Repository.BlogRepo;
import online.ptsports.PTSports.Repository.CategoryRepo;
import online.ptsports.PTSports.Service.BlogService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {
    private final BlogRepo blogRepo;
    private final ModelMapper modelMapper;
    private final CategoryRepo categoryRepo;

    @Autowired
    public BlogServiceImpl(BlogRepo blogRepo, ModelMapper modelMapper, CategoryRepo categoryRepo) {
        this.blogRepo = blogRepo;
        this.modelMapper = modelMapper;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public BlogDto createBlog(BlogDto blogDto) {
        Blog blog = convertToBlog(blogDto);
        blogRepo.save(blog);
        return convertToBlogDto(blog);
    }

    @Override
    public BlogDto updateBlog(BlogDto blogDto, Integer blogId) {
        Blog blog = blogRepo.findById(blogId)
                .orElseThrow(() -> new ResoureNotFoundException("Blog", "ID", blogId));

        // Update other properties of the blog entity
        blog.setTitle(blogDto.getTitle());
        blog.setContent(blogDto.getContent());

        // Retrieve the category as needed
        // Example: Category category = categoryRepo.findById(blogDto.getCategoryId()).orElse(null);
        // Set the category to the blog entity
        // blog.setCategory(category);

        blogRepo.save(blog);
        return convertToBlogDto(blog);
    }

    @Override
    public BlogDto getBlogById(Integer blogId) {
        Blog blog = blogRepo.findById(blogId)
                .orElseThrow(() -> new ResoureNotFoundException("Blog", "ID", blogId));

        return convertToBlogDto(blog);
    }

    @Override
    public List<BlogDto> getAllBlogs() {
        List<Blog> blogs = blogRepo.findAll();
        return blogs.stream()
                .map(this::convertToBlogDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteBlog(Integer blogId) {
        Blog blog = blogRepo.findById(blogId)
                .orElseThrow(() -> new ResoureNotFoundException("Blog", "ID", blogId));
        blogRepo.deleteById(blog.getBlogId());
    }

    @Override
    public BlogDto convertToBlogDto(Blog blog) {
        return modelMapper.map(blog, BlogDto.class);
    }

    @Override
    public Blog convertToBlog(BlogDto blogDto) {
        return modelMapper.map(blogDto, Blog.class);
    }
}

