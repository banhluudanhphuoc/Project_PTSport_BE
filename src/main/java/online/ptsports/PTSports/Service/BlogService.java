package online.ptsports.PTSports.Service;


import online.ptsports.PTSports.DTO.BlogDto;
import online.ptsports.PTSports.Entity.Blog;

import java.util.List;

public interface BlogService {
    BlogDto createBlog(BlogDto blogDto);
    BlogDto updateBlog(BlogDto blogDto,Integer BlogId);
    BlogDto getBlogById(Integer blogId);
    List<BlogDto> getAllBlogs();
    void deleteBlog(Integer blogId);

    BlogDto convertToBlogDto(Blog blog);
    Blog convertToBlog(BlogDto blogDto);
}
