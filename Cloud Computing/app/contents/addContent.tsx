"use client";
import { useState, SyntheticEvent } from "react";
import type { Article } from "@prisma/client";
import { useRouter } from "next/navigation";
import axios from "axios";

const AddContent = ({articles}: {articles : Article[]}) => {
    const [author, setAuthor] = useState("");
    const [title, setTitle] = useState("");
    const [subtitle, setSubtitle] = useState("");
    const [image, setImage] = useState(""); 
    const [article, setArticle] = useState(""); 

    const [isOpen, setIsOpen] = useState(false);
    const router = useRouter();

    const handleSubmit = async (e: SyntheticEvent) => {
        e.preventDefault();
        await axios.post('/api/contents', {
            author: author,
            title: title,
            subtitle: subtitle,
            image: image,
            articleId: Number(article)
        })
        setAuthor("");
        setTitle("");
        setSubtitle("");
        setImage("");
        setArticle("");
        router.refresh();
        setIsOpen(false);
    }

    const handleModal = () => {
        setIsOpen(!isOpen);
    }

    return (
    <div>
        <button className="btn" onClick={handleModal}>Add New</button>

        <div className={isOpen ? 'modal modal-open' : 'modal'}>
            <div className="modal-box">
                <h3 className="font-bold text-lg">Add New Content</h3>
                <form onSubmit={handleSubmit}>
                    <div className="form-control w-full">
                        <label className="label font-bold">Author Name</label>
                        <input 
                        type="text"
                        value={author}
                        onChange={(e) => setAuthor(e.target.value)}
                        className="input input-bordered" 
                        placeholder="Author Name"/>
                    </div>
                    <div className="form-control w-full">
                        <label className="label font-bold">Title</label>
                        <input 
                        type="text" 
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                        className="input input-bordered" 
                        placeholder="Title"/>
                    </div>
                    <div className="form-control w-full">
                        <label className="label font-bold">Subtitle</label>
                        <input 
                        type="text" 
                        value={subtitle}
                        onChange={(e) => setSubtitle(e.target.value)}
                        className="input input-bordered" 
                        placeholder="Subtitle"/>
                    </div>
                    <div className="form-control w-full">
                        <label className="label font-bold">Image</label>
                        <input 
                        type="text" 
                        value={image}
                        onChange={(e) => setImage(e.target.value)}
                        className="input input-bordered" 
                        placeholder="Image"/>
                    </div>
                    <div className="form-control w-full">
                        <label className="label font-bold">Category</label>
                        <select 
                        value={article}
                        onChange={(e) => setArticle(e.target.value)}
                        className="select select-bordered">
                        <option value="" disabled>Select Category</option>
                        {articles.map((article) => (
                            <option value={article.id} key={article.id}>{article.category}</option>
                        ))}
                        </select>
                    </div>
                    <div className="modal-action">
                        <button type="button" className="btn" onClick={handleModal}>Close</button>
                        <button type="submit" className="btn btn-primary">Save</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
  )
}

export default AddContent