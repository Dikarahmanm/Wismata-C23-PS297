"use client";
import { useState, SyntheticEvent } from "react";
import type { Article } from "@prisma/client";
import { useRouter } from "next/navigation";
import axios from "axios";

type Content = {
    id: number;
    author: string;
    title: string;
    subtitle: string;
    image: string;
    articleId: number;
};

const UpdateContent = ({
    articles, 
    content, 
    }: {
    articles : Article[];
    content: Content
    }) => {
    const [author, setAuthor] = useState(content.author);
    const [title, setTitle] = useState(content.title);
    const [subtitle, setSubtitle] = useState(content.subtitle);
    const [image, setImage] = useState(content.image); 
    const [article, setArticle] = useState(content.articleId); 

    const [isOpen, setIsOpen] = useState(false);
    const router = useRouter();

    const handleUpdate = async (e: SyntheticEvent) => {
        e.preventDefault();
        await axios.patch(`/api/contents/${content.id}`, {
            author: author,
            title: title,
            subtitle: subtitle,
            image: image,
            articleId: Number(article)
        })
        router.refresh();
        setIsOpen(false);
    }

    const handleModal = () => {
        setIsOpen(!isOpen);
    }

    return (
    <div>
        <button className="btn btn-info btn-sm" onClick={handleModal}>
            Edit
        </button>

        <div className={isOpen ? 'modal modal-open' : 'modal'}>
            <div className="modal-box">
                <h3 className="font-bold text-lg">Edit Content</h3>
                <form onSubmit={handleUpdate}>
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
                        onChange={(e) => setArticle(Number(e.target.value))}
                        className="select select-bordered">
                        {articles.map((article) => (
                            <option value={article.id} key={article.id}>{article.category}</option>
                        ))}
                        </select>
                    </div>
                    <div className="modal-action">
                        <button type="button" className="btn" onClick={handleModal}>
                            Close
                        </button>
                        <button type="submit" className="btn btn-primary">
                            Update
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
  )
}

export default UpdateContent